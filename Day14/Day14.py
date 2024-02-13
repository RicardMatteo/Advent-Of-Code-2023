#importing json
import json

lines = [list(line) for line in open('Day14/input').read().split('\n')]

def print_matrix(matrix):
    for i in range(len(matrix)):
        print("".join(lines[i]))
    return


def tilt(lines)->list:
    # For each coloumn
    for j in range(len(lines[0])):
        c = 0
        # For each element in the coloumn
        for i in range(len(lines), 0, -1):
            # If we find a cube-shape rock
            if lines[i-1][j] == '#' and c > 0:
                for k in range(i, i+c):
                    lines[k][j] = 'O'
                c = 0
                
            # If we find a round rock
            elif lines[i-1][j] == 'O':
                c+=1
                lines[i-1][j] = '.'
        # at the end of the coloumn, we do the same as if we found a cube-shape rock above
        for k in range(c):
            lines[k][j] = 'O'
    return lines


def cost(matrix):
    return sum([sum([len(matrix[j])-i for j in range(len(matrix[i])) if matrix[i][j] == 'O']) for i in range(len(matrix))])


# Rotate the matrix 90 degrees clockwise
def rotate_clockwise(matrix):
    return [[matrix[j][i] for j in range(len(matrix[i])-1, -1, -1)] for i in range(len(matrix))]

# part 1
print("part 1 :", cost(tilt(lines)))

# part 2
c, dict, found, cycles = 0, {}, False, 1_000_000_000 * 4
while (c < cycles):
    lines = tilt(lines)
    hash = json.dumps(lines)
    if not found:
        if hash in dict:
            found = True
            c = cycles - (cycles - c) % (c - dict[hash])
        dict[hash] = c
    lines = rotate_clockwise(lines)
    c += 1
print("part 2 :", cost(lines))
    
    