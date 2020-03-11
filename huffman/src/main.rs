use std::boxed::Box;
use std::char;
use std::cmp::Ordering;
use std::cmp::PartialOrd;
use std::collections::BTreeMap;
use std::collections::BinaryHeap;
use std::collections::HashMap;
use std::env;
use std::error::Error;
use std::fs;
use std::fs::File;
use std::io::prelude::Write;
use std::io::BufRead;
use std::io::BufReader;
use std::io::Read;
use std::path::Path;
use std::str;
use std::u32;
extern crate time;
use time::PreciseTime;

pub struct Canonical {
    key: char,
    code: String,
}

impl Eq for Canonical {}

impl PartialEq for Canonical {
    fn eq(&self, other: &Canonical) -> bool {
        other.key == self.key
    }
}

impl PartialOrd for Canonical {
    fn partial_cmp(&self, other: &Canonical) -> Option<Ordering> {
        match other.code.len().partial_cmp(&self.code.len()) {
            Some(Ordering::Equal) => other.key.partial_cmp(&self.key),
            _ => other.code.len().partial_cmp(&self.code.len()),
        }
    }
}

impl Ord for Canonical {
    fn cmp(&self, other: &Canonical) -> Ordering {
        match other.code.len().cmp(&self.code.len()) {
            Ordering::Equal => other.key.cmp(&self.key),
            _ => other.code.len().cmp(&self.code.len()),
        }
    }
}

#[allow(dead_code)]
pub struct Node {
    key: char,
    freq: u32,
    left: Option<Box<Node>>,
    right: Option<Box<Node>>,
}

impl Node {
    fn is_leaf(&self) -> bool {
        self.left.is_none() && self.right.is_none()
    }
}

impl Eq for Node {}

impl PartialEq for Node {
    fn eq(&self, other: &Node) -> bool {
        other.freq == self.freq
    }
}

impl PartialOrd for Node {
    fn partial_cmp(&self, other: &Node) -> Option<Ordering> {
        other.freq.partial_cmp(&self.freq)
    }
}

impl Ord for Node {
    fn cmp(&self, other: &Node) -> Ordering {
        other.freq.cmp(&self.freq)
    }
}

pub struct FileData {
    name: String,
    table: String,
    byte_vec: Vec<u8>,
    modulus: usize,
    format: String,
}

impl Clone for FileData {
    fn clone(&self) -> FileData {
        FileData {
            name: self.name.clone(),
            table: self.table.clone(),
            byte_vec: self.byte_vec.clone(),
            modulus: self.modulus,
            format: self.format.clone(),
        }
    }
}

fn condense_bytes(input: String) -> Vec<u8> {
    let mut bytes: Vec<u8> = Vec::new();
    for chunk in input.as_bytes().chunks(8) {
        let string = String::from_utf8(chunk.to_vec()).unwrap();
        let bit_val = u8::from_str_radix(&string, 2).unwrap();
        bytes.push(bit_val);
    }

    bytes
}

fn uncondense_bytes(input: Vec<u8>, last: usize) -> String {
    let mut output: String = String::new();
    let mut iter_count = input.iter().count();
    for byte in input.iter() {
        if iter_count > 1 {
            let bin_string = &format!("{:08b}", byte);
            output.push_str(bin_string);
            iter_count -= 1;
            continue;
        }
        let bin_string = &format!("{:0width$b}", byte, width = last);
        output.push_str(bin_string);
    }

    output
}

fn validate_command(command: String) -> u32 {
    match command.as_ref() {
        "compress" => 1,
        "decompress" => 2,
        _ => panic!("Unknown command."),
    }
}

fn validate_filename(filename: String) -> (String, String) {
    let i = filename.find(".");
    match i {
        Some(a) => {
            let (name, filetype) = filename.split_at(a);
            (name.to_string(), filetype.to_string())
        }
        None => (filename, String::from("folder")),
    }
}

fn frequency(_contents: String) -> BTreeMap<char, u32> {
    let mut count = BTreeMap::new();
    for c in _contents.chars() {
        *count.entry(c).or_insert(0) += 1;
    }

    count
}

fn make_canonical(map: HashMap<char, String>) -> BTreeMap<char, String> {
    let mut canonical: BTreeMap<char, String> = BTreeMap::new();
    let mut heap: BinaryHeap<Canonical> = BinaryHeap::new();
    for (key, val) in map.iter() {
        let c = Canonical {
            key: *key,
            code: val.to_string(),
        };
        heap.push(c);
    }

    let initial_size = heap.len();
    let mut accumulator: String = "0".to_string();
    let mut previous: String = String::new();
    while !heap.is_empty() {
        let pair = heap.peek();
        let bit_length = pair.unwrap().code.len();
        if initial_size == (heap.len()) {
            let repeated = "0".repeat(bit_length);
            canonical.insert(pair.unwrap().key, repeated.clone());
            accumulator = repeated;
            heap.pop();
            previous = accumulator.clone();
            continue;
        }
        let mut bit_val = u32::from_str_radix(&accumulator, 2).expect("Parsing error.");
        bit_val += 1;
        accumulator = format!("{:0zeros$b}", bit_val, zeros = previous.len());
        while accumulator.len() < bit_length {
            accumulator.push('0');
        }
        canonical.insert(pair.unwrap().key, accumulator.clone());
        previous = accumulator.clone();
        heap.pop();
    }

    return canonical;
}

fn parse_code(code: String) -> BTreeMap<String, u32> {
    let mut map: BTreeMap<String, u32> = BTreeMap::new();
    let v: Vec<&str> = code.split(":").collect();
    for s in v.iter() {
        let entries: Vec<&str> = s.split('锈').collect();
        if entries.is_empty() {
            break;
        }
        let a = match entries.get(1) {
            Some(_a) => entries.get(1).unwrap().to_string(),
            None => " ".to_string(),
        };

        let ascii: u32 = match entries.get(0).unwrap().to_string().parse() {
            Ok(a) => a,
            Err(_e) => continue,
        };
        map.insert(a, ascii);
    }

    return map;
}

fn is_binary(filename: String) -> bool {
    let f = File::open(filename).expect("File could not be opened.");
    let mut file = BufReader::new(&f);
    let mut code_format: String = String::new();
    let mut fill: String = String::new();
    let mut _filetype: String = String::new();

    file.read_line(&mut code_format)
        .expect("Could not read code line.");
    file.read_line(&mut fill).expect("Could not read fill.");
    file.read_line(&mut _filetype)
        .expect("Could not read filetype line.");

    let filetype = _filetype.trim();
    match filetype.as_ref() {
        ".txt" | "folder" => false,
        _ => true,
    }
}

fn is_folder(filename: String) -> bool {
    let f = File::open(filename).expect("File could not be opened");
    let mut file = BufReader::new(&f);
    let mut first_line: String = String::new();
    file.read_line(&mut first_line)
        .expect("Could not read first line");
    match first_line.contains("\\") {
        true => true,
        false => false,
    }
}

fn build_compression_table(
    root: &Option<Box<Node>>,
    mut new_code: String,
    map: &mut HashMap<char, String>,
) {
    let n = match root.as_ref() {
        Some(x) => x,
        None => return,
    };

    if n.is_leaf() {
        if new_code.is_empty() {
            new_code.push('0');
        }

        map.insert(n.key, new_code.clone());
    }

    let mut l = new_code.clone();
    l.push('0');
    let mut r = new_code;
    r.push('1');

    build_compression_table(&n.left, l, map);
    build_compression_table(&n.right, r, map);
}

fn build_huffman_tree(mut heap: BinaryHeap<Box<Node>>) -> Option<Box<Node>> {
    assert!(!heap.is_empty());
    let root;
    loop {
        let l = heap.pop().unwrap();
        let r = match heap.pop() {
            Some(x) => x,
            None => {
                root = l;
                break;
            }
        };
        let n: Box<Node> = Box::new(build_parent(l, r));
        if heap.is_empty() {
            root = n;
            break;
        } else {
            heap.push(n);
        }
    }

    Some(root)
}

fn build_parent(left: Box<Node>, right: Box<Node>) -> Node {
    Node {
        key: '$',
        freq: right.freq + left.freq,
        left: Some(left),
        right: Some(right),
    }
}

fn build_min_heap(map: BTreeMap<char, u32>) -> BinaryHeap<Box<Node>> {
    let mut heap = BinaryHeap::new();
    for (key, val) in map.iter() {
        let b: Box<Node> = Box::new(construct_node(*key, *val));
        heap.push(b);
    }

    heap
}

fn construct_node(c: char, x: u32) -> Node {
    Node {
        left: None,
        right: None,
        key: c,
        freq: x,
    }
}

fn compress(contents: String, output: String, filetype: String) {
    let count = frequency(contents.clone());
    let heap: BinaryHeap<Box<Node>> = build_min_heap(count.clone());
    let root_node = build_huffman_tree(heap);
    let mut compression_table: HashMap<char, String> = HashMap::new();
    build_compression_table(&root_node, String::from(""), &mut compression_table);
    let canonical_table = make_canonical(compression_table.clone());

    for (key, val) in canonical_table.iter() {
        println!(
            "Key: {:?}\tFrequency: {}\tCode: {}\tCanonical: {}",
            key,
            count.get(key).unwrap(),
            compression_table.get(key).unwrap(),
            val
        );
    }

    let path = Path::new(&output);
    let display = path.display();
    let mut file = match File::create(&path) {
        Err(why) => panic!("Couldn't create {}: {}", display, why.description()),
        Ok(file) => file,
    };
    let mut buffer: String = String::new();
    for c in contents.chars() {
        match canonical_table.get(&c) {
            Some(a) => buffer.push_str(a),
            None => continue,
        }
    }

    for (key, _) in canonical_table.iter() {
        match canonical_table.get(&key) {
            Some(a) => {
                let k = key.clone();
                file.write(&format!("{}", k as u32).as_bytes())
                    .expect("Unable to write to file.");
                file.write("锈".as_bytes())
                    .expect("Unable to write to file.");
                file.write(a.as_bytes()).expect("Unable to write to file.");
                file.write(":".as_bytes())
                    .expect("Unable to write to file.");
            }
            None => continue,
        };
    }

    file.write(&format!("\n{}\n{}\n", (buffer.len() % 8), filetype).as_bytes())
        .unwrap();
    let byte_vec = condense_bytes(buffer.clone());
    file.write(&byte_vec).unwrap();
    println!(
        "Compression ratio: {}",
        file.metadata().unwrap().len() as f32 / contents.len() as f32
    );
}

fn compact_compress(contents: String, filename: String, filetype: String) -> Box<FileData> {
    let count = frequency(contents.clone());
    let heap: BinaryHeap<Box<Node>> = build_min_heap(count.clone());
    let root_node = build_huffman_tree(heap);
    let mut compression_table: HashMap<char, String> = HashMap::new();
    build_compression_table(&root_node, String::from(""), &mut compression_table);
    let canonical_table = make_canonical(compression_table.clone());

    for (key, val) in canonical_table.iter() {
        println!(
            "Key: {:?}\tFrequency: {}\tCode: {}\tCanonical: {}",
            key,
            count.get(key).unwrap(),
            compression_table.get(key).unwrap(),
            val
        );
    }

    let mut buffer: String = String::new();
    for c in contents.chars() {
        match canonical_table.get(&c) {
            Some(a) => buffer.push_str(a),
            None => continue,
        }
    }

    let mut table: String = String::new();

    for (key, _) in canonical_table.iter() {
        match canonical_table.get(&key) {
            Some(a) => {
                let k = key.clone();
                table.push_str(&format!("{}锈{}:", k as u32, a));
            }
            None => continue,
        };
    }

    let byte_vec = condense_bytes(buffer.clone());
    let data: FileData = FileData {
        name: filename,
        table: table,
        byte_vec: byte_vec,
        modulus: (buffer.len() % 8),
        format: filetype,
    };
    //file.write(&format!("\n{}\n{}\n", (buffer.len() % 8), filetype).as_bytes())
    //    .unwrap();
    //file.write(&byte_vec).unwrap();

    return Box::new(data);
}

fn folder_compression(filename: String, output: String) {
    let paths = fs::read_dir(&filename).unwrap();
    let mut f = File::create(output).unwrap();
    for path in paths {
        let entry = path.unwrap().path();
        let filepath = entry.as_path().to_str().unwrap();
        let _file = validate_filename(filepath.to_string());
        let file_output: String = _file.0;
        let output_filetype: String = _file.1;
        println!("filetype: {} \t output: {}", output_filetype, file_output);
        let contents = fs::read_to_string(filepath).expect("Something went wrong reading the file");
        let data = compact_compress(contents, file_output, output_filetype);
        f.write(
            &format!(
                "{}\n{}\n{}\n{}\n",
                data.name, data.table, data.modulus, data.format
            )
            .as_bytes(),
        )
        .unwrap();
        f.write(&data.byte_vec).unwrap();
        f.write("\n".as_bytes()).unwrap();
    }
}

fn folder_decompression(filename: String, output: String) {
    let f = File::open(filename).expect("File could not be opened.");
    let mut file = BufReader::new(&f);
    let mut contents: Vec<u8> = Vec::new();
    file.read_to_end(&mut contents).expect("read_to_end.");
    let split = contents.split(|num| num == &(10 as u8));
    let mut valid_vec: Vec<String> = Vec::new();
    let mut invalid_vec: Vec<Vec<u8>> = Vec::new();
    for s in split {
        let line = match String::from_utf8(s.to_vec()) {
            Ok(a) => match a.is_empty() {
                false => a,
                true => continue,
            },
            Err(_e) => {
                invalid_vec.push(s.to_vec());
                continue;
            }
        };
        valid_vec.push(line);
    }

    let mut files_data: Vec<FileData> = Vec::new();
    let mut name: String = String::new();
    let mut table: String = String::new();
    let mut modulus: usize = 0;
    let mut format: String = String::new();
    for i in 0..invalid_vec.len() {
        for j in 1..5 {
            match j {
                1 => {
                    name = valid_vec
                        .get((j + (invalid_vec.len() * i)) - 1)
                        .unwrap()
                        .to_string()
                }
                2 => {
                    table = valid_vec
                        .get((j + (invalid_vec.len() * i)) - 1)
                        .unwrap()
                        .to_string()
                }
                3 => {
                    modulus = usize::from_str_radix(
                        valid_vec.get((j + (invalid_vec.len() * i)) - 1).unwrap(),
                        10,
                    )
                    .unwrap()
                }
                4 => {
                    format = valid_vec
                        .get((j + (invalid_vec.len() * i)) - 1)
                        .unwrap()
                        .to_string()
                }
                _ => continue,
            };
        }

        files_data.push(FileData {
            name: name.clone(),
            table: table.clone(),
            modulus: modulus.clone(),
            format: format.clone(),
            byte_vec: invalid_vec.get(i).unwrap().to_vec(),
        });
    }

    let folder = fs::create_dir(&format!("_{}", output)).expect("Could not create directory.");

    for file in files_data.iter() {
        compact_decompress(Box::new(file.clone()), false);
    }
}

fn compact_decompress(file: Box<FileData>, binary: bool) {
    let _decompression_table = parse_code(file.table);
    let bin_data = uncondense_bytes(file.byte_vec, file.modulus);
    let mut input_buffer: String = String::new();
    let mut output_buffer: String = String::new();
    for c in bin_data.chars() {
        input_buffer.push(c);
        match _decompression_table.get(&input_buffer) {
            Some(a) => {
                if !binary {
                    output_buffer.push(char::from_u32(*a).unwrap());
                } //else {
                  //output_buffer.push_str(&String::from_utf8_lossy(a.as_bytes()));
                  //}
            }
            None => continue,
        };

        input_buffer.clear();
    }

    let mut output_name: String = String::new();
    output_name.push_str(&format!("_{}{}", file.name, file.format));
    let mut output_file = File::create(&output_name).expect("Could not create output file.");
    output_file
        .write_all(output_buffer.as_bytes())
        .expect("Could not decompress file.");
}

fn decompress(filename: String, _output: String, binary: bool) {
    let f = File::open(filename).expect("File could not be opened.");
    let mut file = BufReader::new(&f);
    let mut code_format: String = String::new();
    let mut _filetype: String = String::new();
    let mut last_chunk: String = String::new();
    let mut data: Vec<u8> = Vec::new();

    file.read_line(&mut code_format)
        .expect("Could not read code line.");
    file.read_line(&mut last_chunk)
        .expect("Could not read last chunk.");
    file.read_line(&mut _filetype)
        .expect("Could not read filetype line.");
    let filetype = _filetype.trim_end();
    file.read_to_end(&mut data).expect("Could not read data.");
    let _decompression_table = parse_code(code_format);

    let last_chunk_val = usize::from_str_radix(&last_chunk.trim(), 10).unwrap();

    let bin_data = uncondense_bytes(data, last_chunk_val);

    let mut input_buffer: String = String::new();
    let mut output_buffer: String = String::new();

    for c in bin_data.chars() {
        input_buffer.push(c);
        match _decompression_table.get(&input_buffer) {
            Some(a) => {
                if !binary {
                    output_buffer.push(char::from_u32(*a).unwrap());
                } //else {
                  //output_buffer.push_str(&String::from_utf8_lossy(a.as_bytes()));
                  //}
            }
            None => continue,
        };

        input_buffer.clear();
    }

    let mut output_name: String = String::new();

    output_name.push_str(&format!("{}_decompressed{}", _output, filetype));

    let mut output_file = File::create(&output_name).expect("Could not create output file.");

    output_file
        .write_all(output_buffer.as_bytes())
        .expect("Could not decompress file.");
}

fn main() {
    let args: Vec<String> = env::args().collect();
    let command = &args[1];
    let filename = &args[2];
    let operation = validate_command(command.to_string());
    let _file = validate_filename(filename.clone().to_string());
    let mut output: String = _file.0;
    let decompressed_file: String = output.clone();
    let filetype: String = _file.1;
    output.push_str(".hoff");
    if operation == 1 {
        match filetype.as_ref() {
            ".txt" => {
                let start = PreciseTime::now();
                let contents =
                    fs::read_to_string(filename).expect("Something went wrong reading the file");
                compress(contents, output.clone(), filetype.clone());
                let end = PreciseTime::now();
                println!(
                    "Time of compression: {}",
                    start.to(end).num_microseconds().unwrap()
                );
            }
            "folder" => folder_compression(filename.to_string(), output.clone()),
            _ => return,
        };
    }
    if operation == 2 {
        match filetype.as_ref() {
            ".hoff" => {
                if !is_folder(filename.to_string()) {
                    let start = PreciseTime::now();
                    decompress(
                        filename.to_string(),
                        decompressed_file,
                        is_binary(filename.to_string()),
                    );
                    let end = PreciseTime::now();
                    println!(
                        "Time of decompression: {}",
                        start.to(end).num_microseconds().unwrap()
                    );
                } else {
                    let folder_name = output.replace(".hoff", "");
                    folder_decompression(filename.to_string(), folder_name);
                }
            }
            _ => panic!("The file was not produced by this program."),
        };
    }
}
