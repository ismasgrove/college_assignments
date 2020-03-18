#!/usr/bin/env python3
# Import socket module
import socket


def Main():
    # local host IP '127.0.0.1'
    host = '127.0.0.1'
    port = 12355

    s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

    # connect to server on local computer
    s.connect((host, port))


    while True:

        message = input("Enter your command in the right format: ")

        box = message
        box = box.split(' ')
        if box[0] == "GET":
            # message sent to server
            s.send(message.encode('ascii'))
            with open(box[1], 'wb') as f:
                # print("file opened")
                flag = 1
                while True:
                    # print('receiving data...')
                    data = s.recv(1024)
                    if flag == 2:
                        print("HTTP/1.0 200 OK\\r\\n")
                    if not data:
                        if flag == 1:
                            print("HTTP/1.0 404 Not Found\\r\\n")
                        print("DONE!")
                        break
                    f.write(data)
                    flag += 1
            f.close()
            s.close()
            break
        elif box[0] == "POST":
            # message sent to server
            s.send(message.encode('ascii'))

            try:
                filename = box[1]
                filename = str(filename)
                f = open(filename, 'rb')
                l = f.read(1024)
                while (l):
                    s.send(l)
                    # print('Sent ', repr(l))
                    l = f.read(1024)
                f.close()
                print("HTTP/1.0 200 OK\\r\\n")
                # print_lock.release()
                break
            except FileNotFoundError:
                print("HTTP/1.0 404 Not Found\\r\\n")
                break

            s.close()
            break
        else:
            print("WRONG FORMAT")


if __name__ == '__main__':
	Main()
