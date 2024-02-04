inputs = open('Day13/input').read()

def findMirror(lines, diffGoal = 0):
    
    # horizontal mirror
    for i in range(1, len(lines)):
        current_diff = diff(lines[i], lines[i-1])
        if (current_diff < diffGoal + 1):
            # potential mirror
            offset = 1
            while (offset < min(len(lines) - i,i) and current_diff <= diffGoal):
                current_diff += diff(lines[i + offset], lines[i - 1 - offset])
                offset += 1
                
            if (current_diff == diffGoal):
                return i * 100
    
    # vertical mirror
    for i in range(1, len(lines[0])):
        current_diff = diff([line[i] for line in lines], [line[i-1] for line in lines])
        if (current_diff <= diffGoal):
            # potential mirror
            offset = 1
            while (offset < min(len(lines[0]) - i,i) and current_diff <= diffGoal):
                current_diff += diff([line[i + offset] for line in lines], [line[i - 1 - offset] for line in lines])
                offset += 1
            
            if (current_diff == diffGoal):
                return i


    print("no mirror found")
    return 1_000_000

def diff(l1, l2):
    return sum([1 for i in range(min(len(l1), len(l2))) if l1[i] != l2[i]])


# Main
blocs = list(map(lambda bloc: bloc.split('\n'),inputs.split('\n\n')))

# Main
part1,part2 = 0,0
for bloc in blocs:
    part1 += findMirror(bloc)
    part2 += findMirror(bloc, 1) 
    
print(part1, part2)
