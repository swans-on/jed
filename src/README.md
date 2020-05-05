# jed
## Java ed line editor
---

From Wikipedia:
>ed (pronounced as distinct letters) is a line editor for Unix and Unix-like operating systems. It was one of the first parts of the Unix operating system that was developed, in August 1969.

This program was an assignment for my Algorithms and Data Structures course. This exercise required us to create our own version of the UNIX ed line editor using a data structure of our choice. I chose to use a linked list in which each line written by the user was its own node. I implemented a variety of commands that were a part of the original ed editor.

Here is the list of commands:
- 1 - Go to the top.
- a - Add text after current line until . on its own line
- d - Delete current line.
- h - Get help.
- i - Like append, but add lines before current line.
- n - Toggle whether line numbers are displayed.
- p - Print current line.
- q! - Abort without write.
- w - Write file to disk.
- x! - Exit with write.
- $ - Go to the last line.
- \- - Go up one line.
- \+ - Go down one line.
- \= - Print current line number.
- \# - Print number of lines and characters in file.


I haven't touched this code since I completed this assigment back in 2019, however, I may go back and change a few things in the future.