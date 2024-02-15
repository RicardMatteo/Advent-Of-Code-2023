inputs = open('Day15/input').read().split(',')

part1 = 0

def hash(string:str):
    res = 0
    for c in string:
        ascii = ord(c) # Convert to ASCII
        res += ascii   # Add to result
        res *= 17      # Multiply by 17    
        res %= 256     # Modulo 256
    return res
    
boxs = {}
for input in inputs:
    part1 += hash(input)
    
    # part 2 : Create a dictionary of boxes
    # If the input ends with '=', we add the substring to the box
    if input[-2] == '=':
        # Find the box
        substring = input[:len(input) - 2];
        box = hash(substring)
        
        # Add to box
        if box in boxs:
            boxs[box][substring] = input[-1]
        else:
            boxs[box] = {substring : input[-1]}
            
    # If the input ends with '-', we remove the substring from the box
    else:
        # Find the box
        substring = input[:len(input) - 1];
        box = hash(substring)
        # Remove from the box
        if box in boxs and substring in boxs[box]:
            boxs[box].pop(substring)
            if len(boxs[box]) == 0:
                boxs.pop(box)
    
# part 2 : Calculate the result    
part2 = 0
for bnumber, box in boxs.items():
    for i,value in enumerate(boxs[bnumber].values()):
        part2 += (bnumber + 1) * (i + 1) * int(value);
    
print("part 1 :",part1)
print("part 2 :",part2)
