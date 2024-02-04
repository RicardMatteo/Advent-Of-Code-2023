from copy import deepcopy
from itertools import combinations


space = list(open('Day11/input'))

# find the galaxies
def find_galaxies(space):
    galaxies = []
    for r, row in enumerate(space):
        for c, cell in enumerate(row):
            if cell == '#':
                galaxies.append([r, c])
    return galaxies


# find the empty lines and columns
def find_empty(space):
    empty_line = []
    empty_column = []
    for r, row in enumerate(space):
        if '#' not in row:
            empty_line.append(r)
    for c in range(len(space[0])):
        if '#' not in [space[r][c] for r in range(len(space))]:
            empty_column.append(c)
    return empty_line, empty_column

# find the distance between two galaxies (manhattan distance)
def distance(g1, g2):
    return abs(g1[0] - g2[0]) + abs(g1[1] - g2[1])

# offset the galaxies
def offset(galaxies, empty_line, empty_column, offset=1):
    for galaxy in galaxies:
        galaxy[0] += sum([offset for line in empty_line if line < galaxy[0]])
        galaxy[1] += sum([offset for column in empty_column if column < galaxy[1]])
    return galaxies

# main function
def main(space):
    galaxies = find_galaxies(space);
    empty_line, empty_column = find_empty(space);
    galaxies1 = offset(deepcopy(galaxies), empty_line, empty_column);
    galaxies2 = offset(galaxies, empty_line, empty_column, 1_000_000-1);
    
    distances = [distance(g1, g2) for g1, g2 in combinations(galaxies1, 2)]
    distances2 = [distance(g1, g2) for g1, g2 in combinations(galaxies2, 2)]
    return sum(distances), sum(distances2)

print(main(space));