#include <iostream>
#include <string>
#include <vector>

using namespace std;

/*sorry it's messy
it's rush hour
input format is a,b,c,d,e,f no spaces*/

int checkAlphabet(string alpha);
struct transitionFunction formatTransition(string input);
void go(vector <struct transitionFunction> tfunc, struct configuration current);
int match(vector <struct transitionFunction> tfunc, int state, char read);
vector<char> getAlphabet(string alpha);

struct configuration{
    int state;
    string tape;
    int head;
    char decision;
};

struct transitionFunction{
    int state;
    char read;
    int newstate;
    char write;
    char decision;
};

/*
1,b,1,#,R
1,a,2,a,R
2,b,2,#,Y
2,a,1,a,N
*/


int main()
{
    int n, head;
    string alpha, transition, tape;
    vector <transitionFunction> tfunction;
    vector<char>alphabet;
    struct configuration current;
    cout<<"Enter number of states:\t";
    cin>>n;
    cout<<"Enter your alphabet\n(Comma delimited, including the '<' symbol if you need it, empty space '#' is accounted for):\t";
    cin>>alpha;
    int v = checkAlphabet(alpha);
    alphabet = getAlphabet(alpha);
    cout<<"Enter transition function(Comma delimited):"<<endl;
    for (size_t i=0; i<(v+1)*n; i++){ //input states must be exhaustive
        cin>>transition;
        tfunction.push_back(formatTransition(transition));
    }
    cout<<"Enter input tape:\t";
    cin>>tape;
    cout<<"Enter position of head:\t";
    cin>>head;

    current.state=0;
    current.head=head;
    current.tape=tape;

    go(tfunction, current);

    return 0;
}

vector<char> getAlphabet(string alpha){
    vector<char> alphabet;
    for (size_t i=0; i<alpha.length(); i++){
        if (alpha.at(i)!=',')
            alphabet.push_back(alpha.at(i));
    }

    return alphabet;
}

void go(vector <struct transitionFunction> tfunc, struct configuration current){
    while (true){

        if (current.decision=='Y' || current.decision=='N') break;

        if (current.head>(current.tape.length()-1))
            current.tape+='#';

        int index = match(tfunc, current.state, current.tape.at(current.head));
        if (index==-1) {cout<<"not found"<<endl; terminate();}

        current.state=tfunc.at(index).newstate;
        current.tape.at(current.head)=tfunc.at(index).write;
        current.decision=tfunc.at(index).decision;
        if (current.decision=='L' && current.tape.at(current.head)!='<')// && current.tape.at(current.head)!='<')
            current.head--;
        if (current.decision=='R')
            current.head++;
    }

    cout<<"(q"<<current.state<<" , "<<current.tape<<" , "<<current.decision<<')'<<endl;
}

int match(vector <struct transitionFunction> tfunc, int state, char read){
    int i;
    for (i=0; i<tfunc.size(); i++){
        if (tfunc.at(i).state==state && tfunc.at(i).read==read)
            return i;
    }
    return -1;
}

int checkAlphabet(string alpha){
    int c=0;
    for (int i=0; i<alpha.length(); i++){
        //can't replace RESERVED characters from the action set
        if (alpha.at(i)!=',' && alpha.at(i)!=' ' && alpha.at(i)!='Y' && alpha.at(i)!='N' && alpha.at(i)!='R' && alpha.at(i)!='L')
            c++;
    }
    return c;
}

struct transitionFunction formatTransition(string input){
    struct transitionFunction trans;
    trans.state=input[0]-'0';
    trans.read=input[2];
    trans.newstate=input[4]-'0';
    trans.write=input[6];
    trans.decision=input[8];
    return trans;
}
