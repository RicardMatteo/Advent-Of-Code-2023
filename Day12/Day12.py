
inputs = list(open('Day12/input'))
Dict = {}
# line : string to parse
# groups : list of integers
# printdecl : string to print (for debug)
def calcLine(line, groups, printdecl = ""):
    key = (line, groups.__str__())
    if key in Dict:
        return Dict[key]
    # print("\033[92m" + printdecl + "\033[0m" + line + "\033[0m", groups)
    if (len(groups) == 0):
        if ('#' in line):
            # print('too many #')
            return 0
        # print('line ok')
        return 1
    if (len(line) < sum(groups) + len(groups) - 1):
        # print('not enough characters')
        return 0
    if (line[0] == '#'):
        if (groups[0] > 1):
            if ('.' in line[:groups[0]]):
                # print('not enough #')
                return 0
            if ((len(line) > groups[0]) and (line[groups[0]] == '#')):
                # print('too many #')
                return 0
            res = calcLine(line[groups[0]+1:], groups[1:], printdecl + '#' * groups[0] + '.')
        else:
            if ((len(line) > 1) and (line[1] == '#')):
                # print('too many #')
                return 0;
            res = calcLine(line[2:], groups[1:], printdecl + '#.')
    elif (line[0] == '.'):
        res = calcLine(line[1:], groups, printdecl + ".")
    elif (line[0] == '?'):
        res = calcLine(line[1:], groups, printdecl + '.') + calcLine('#' + line[1:], groups, printdecl);
    Dict[key] = res
    return res


# Main
for part2 in [False, True]:
    res = 0; 
    c = 0
    for input in inputs:
        c += 1
        line,groups = input.split(' ')
        if (part2):
            line = '?'.join([line] * 5)
            groups = ','.join([groups] * 5)
        resline = calcLine(line, [int(x) for x in groups.split(',')])
        # print("ligne " + str(c) + " " + str(resline))
        res += resline
    print(res)
        