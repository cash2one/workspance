ch3 = lambda x:sum([256**j*int(i) for j,i in enumerate(x.split('.')[::-1])])
print ch3('7.91.205.21')

ch2 = lambda x: '.'.join([str(x/(256**i)%256) for i in range(3,-1,-1)])
print ch2(2343324232)