from numpy import *
import sys

pontos=[]
for i in sys.argv[1:]:
    pontos.append(float(i))

polinomio = poly1d([pontos[0],1])
for i in range(len(pontos)-1):
    polinomio = polymul(polinomio,poly1d([pontos[i+1],1]))
    #print("{} \n\n".format(polinomio))
polinomio = polysub(polinomio,poly1d([1,1]))

lambdaMeasure = 0
for i in polinomio.roots:
    if (i>-1):
        if(isinstance(i, complex)):
            if (i.imag==0):
                lambdaMeasure = float(i)
                break
        else:
            if(i==0):
                continue
            else:
                lambdaMeasure = i
                break
#print(polinomio.roots)
print(lambdaMeasure)
