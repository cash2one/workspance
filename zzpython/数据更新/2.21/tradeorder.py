from operator import itemgetter, attrgetter
listallvip = [('john', 'A', 15,7), ('jane', 'B', 7,3), ('dave', 'B', 7,4),('dave', 'B', 7,2),('dave', 'B', 1,7),('jave', 'A', 2,2),('d','A',4,2)]


def getchangeorder(listall):
    listall = sorted(listall, key=itemgetter(1))
    changeflag="0"
    listallvip1=[]
    listallvip2=[]
    m=0
    for i in listall:
        m+=1
        if (changeflag==str(i[1])):
            list1=[i[0],i[1],i[2],i[3]]
            listallvip2.append(list1)
            if (len(listallvip)==m):
                listallvip1+=listallvip2
        else:
            listallvip2=sorted(listallvip2, key=itemgetter(2,3),reverse=True)
            listallvip1+=listallvip2
            listallvip2=[]
            list1=[i[0],i[1],i[2],i[3]]
            listallvip2.append(list1)
            if (len(listallvip)==m):
                listallvip1+=listallvip2
        changeflag=str(i[1])
    return listallvip1
print getchangeorder(listallvip)
