# import plotly.express as px
import pandas as pd 
import matplotlib.pyplot as plt

datasets=["aggregation","compound","pathbased","spiral","D31","R15","jain","flame"]
rows , columns = 3 , 3
plt.figure(figsize=(40,40))

for row in range(rows):
    for col in range(columns):
        if(row*columns + col>=len(datasets)):
            break
        data = pd.read_csv(datasets[row*columns + col]+".csv") 
        plt.subplot(columns, rows, row*columns + col + 1)
        plt.axis("equal")
        for name, group in data.groupby("species"):
            plt.plot(group["x"], group["y"], marker="o", linestyle="", label=name)
        plt.grid(True)
        plt.title(datasets[row*columns + col],fontsize=45)
        plt.legend(loc="center right",bbox_to_anchor=(1.12, 0.5))

plt.savefig("datasets.pdf",bbox_inches='tight')
plt.show()