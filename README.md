# Pentomino Game Solution

[pentomino](https://en.wikipedia.org/wiki/Pentomino) is a game like jigsaw puzzle. There are always at lease one solution for particular shape of rectangles. But combinatoins and positions of all the pieces are huge. If you want to find all the solutions for a rectangle, it is better to take use of heuristic algorithm.

# Solutions for this game

I  take two search algorithms for solve this game in this project. 1. Dynamic programming with pruning.  2 [Dancing links(algorithm X) of Donald Knuth](https://en.wikipedia.org/wiki/Dancing_Links)  both of which find all the solutions within 1 sec.
#
DLX is a high efficiency and very beautiful algorithm. We can solve all the [exact cover](https://en.wikipedia.org/wiki/Exact_cover) problems like n-queens, Sudoku  with it.
