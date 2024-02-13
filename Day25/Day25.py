inputs = open('Day25/input').read()

vertex = dict()
grp1 = set()
for line in inputs.split('\n'):
    left, *right = line.replace(':','').split()
    grp1.add(left)
    for r in right:
        grp1.add(r) 
        vertex.setdefault(left, set()).add(r)
        vertex.setdefault(r, set()).add(left)




# find the number of links with grp2 which is the complement of grp1
count_links = lambda x: sum([1 for n in vertex[x] if n not in grp1])

grp1.remove(max(grp1, key=count_links))
while sum(map(count_links, grp1)) > 3:
    # remove the vertex with the most links to grp2
    grp1.remove(max(grp1, key=count_links))
    
print(len(grp1) * len(set(vertex)-grp1))