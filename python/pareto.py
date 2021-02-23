#!/usr/bin/env python
# coding: utf-8

# In[11]:


import numpy as np
import matplotlib.pyplot as plt
get_ipython().run_line_magic('matplotlib', 'inline')

import oapackage
import oalib


# In[13]:


datapoints=np.random.rand(2, 100)
for ii in range(0, datapoints.shape[1]):
    w=datapoints[:,ii]
    fac=.6+.4*np.linalg.norm(w)
    datapoints[:,ii]=(1/fac)*w

h=plt.plot(datapoints[0,:], datapoints[1,:], '.b', markersize=16, label='Non Pareto-optimal')
_=plt.title('The input data', fontsize=15)
plt.xlabel('Objective 1', fontsize=16)
plt.ylabel('Objective 2', fontsize=16)


# In[14]:


pareto=oapackage.ParetoDoubleLong()

for ii in range(0, datapoints.shape[1]):
    w=oapackage.doubleVector( (datapoints[0,ii], datapoints[1,ii]))
    pareto.addvalue(w, ii)

pareto.show(verbose=1)


# In[16]:


lst=pareto.allindices() # the indices of the Pareto optimal designs
optimal_datapoints=datapoints[:,lst]

h=plt.plot(datapoints[0,:], datapoints[1,:], '.b', markersize=16, label='Non Pareto-optimal')
hp=plt.plot(optimal_datapoints[0,:], optimal_datapoints[1,:], '.r', markersize=16, label='Pareto optimal')
plt.xlabel('Objective 1', fontsize=16)
plt.ylabel('Objective 2', fontsize=16)
plt.xticks([])
plt.yticks([])
_=plt.legend(loc=3, numpoints=1)


# In[59]:


def dominates(x1, x2):
    # 判断x1是否支配x2
    # x1 & x2: list
    assert len(x1) == len(x2), "x1 must be the same size of x2"
    for i in range(len(x1)):
        if x1[i] > x2[i]:
            return False
    return True

def is_dominated(x1, x2):
    # 判断x1是否被x2支配
    # x1 & x2: list
    assert len(x1) == len(x2), "x1 must be the same size of x2"
    for i in range(len(x1)):
        if x1[i] < x2[i]:
            return False
    return True

def equal(x1, x2):
    # 判断x1是否被x2支配
    # x1 & x2: list
    assert len(x1) == len(x2), "x1 must be the same size of x2"
    for i in range(len(x1)):
        if abs(x1[i] - x2[i]) < 1e-4:
            return False
    return True

class Pareto():
    def __init__(self):
        self.arr = []
    
    def addvalue(self, p_index, p_value):
        new_pareto_elements = []
        delete_index = []
        ii = 0
        while ii < len(self.arr):
            if  dominates(self.arr[ii][1], p_value):
                if equal(self.arr[ii][1], p_value):
                    new_pareto_elements.append([p_index, p_value])
                    return
                else:
                    #print("not a pareto")
                    return
            if is_dominated(self.arr[ii][1], p_value):
                delete_index.append(ii)
                ii += 1
            else:
                ii += 1
        
        self.arr = [self.arr[j] for j in range(0, len(self.arr)) if j not in delete_index]
        self.arr.append([p_index, p_value])


# In[61]:


if __name__ == "__main__":
    
    import pandas as pd
    file_name = "C://Users/GW00205581/Desktop/test_pareto.txt"
    elements = []
    with open(file_name, "r") as f:
        index = 0
        for line in f:
            parts = line.strip().split("\t")
            parts = [float(t) for t in parts][0:]
            elements.append([index, parts])
            index += 1
    
    pareto = Pareto()
    for t in elements:
        pareto.addvalue(t[0], t[1])
    print(pareto.arr)
    
    pareto_opts = []
    for t in pareto.arr:
        pareto_opts.append(t[-1])
    pareto_opts = np.asarray(pareto_opts)
    print(pareto_opts)
    
    elements_ = []
    for t in elements:
        elements_.append(t[-1])
    elements_ = np.asarray(elements_)
        
                
    import matplotlib.pyplot as plt
    plt.figure(figsize=(12, 6))
    plt.plot(elements_[:,0], elements_[:,1], '.b', markersize=16, label='Non Pareto-optimal')
    plt.plot(pareto_opts[:,0], pareto_opts[:,1], '.r', markersize=16, label='Pareto optimal')
    #plt.scatter(pareto_opts[:, 0], pareto_opts[:, 1])
#     plt.xlim([0, 100000])
#     plt.ylim([1, 300])
    plt.show()
    
    from mpl_toolkits.mplot3d import axes3d
    plt.figure("3D Scatter", facecolor="lightgray", figsize=(18, 9))
    ax3d = plt.gca(projection="3d")  # 创建三维坐标
    plt.title('3D Scatter', fontsize=20)
    ax3d.set_xlabel('x', fontsize=14)
    ax3d.set_ylabel('y', fontsize=14)
    ax3d.set_zlabel('z', fontsize=14)
    plt.tick_params(labelsize=10)
    ax3d.scatter(elements_[:,0], elements_[:,1], elements_[:, 2], s=20, c='b', cmap="jet", marker=".")
    ax3d.scatter(pareto_opts[:,0], pareto_opts[:,1], pareto_opts[:, 2], s=20, c='r', cmap="jet", marker="o")
    
        


# In[ ]:





# In[ ]:




