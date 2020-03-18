#!/usr/bin/env python3
import socket
from _thread import *
import threading


# thread function
def threaded(c):
    while True:

        # data received from client
        data = c.recv(1024)
        if not data:
            print('Bye')
            break
        # the comming command
        print(data)

        box = ''.join(chr(i) for i in data)
        box = box.split(' ')

        if box[0] == "GET":
            try:
                filename = box[1]
                filename = str(filename)
                f = open(filename, 'rb')
                l = f.read(1024)
                while (l):
                    c.send(l)
                    # print('Sent ', repr(l))
                    l = f.read(1024)
                f.close()
                break
            except FileNotFoundError:
                break

        elif box[0] == "POST":
            with open(box[1], 'wb') as f:
                # print("file opened")
                while True:
                    # print('receiving data...')
                    data = c.recv(1024)
                    # print('data=%s', (data))
                    if not data:
                        print("DONE!")
                        break
                    f.write(data)
            f.close()
            c.close()
            break
    c.close()


def Main():
    host = ""
    port = 12355
    s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    s.bind((host, port))
    print("socket binded to port", port)

    # put the socket into listening mode
    s.listen(5) # up to 5 connections
    print("socket is listening")

    # a forever loop until client wants to exit
    while True:
        # establish connection with client
        c, addr = s.accept()

        print('Connected to :', addr[0], ':', addr[1])

        # Start a new thread and return its identifier
        start_new_thread(threaded, (c,))
    s.close()


if __name__ == '__main__':
    Main()
