#!/usr/bin/env python
# coding: utf-8

# In[22]:


import sys
import os
import json

import numpy as np
import pandas as pd

from scipy import interpolate


# In[23]:


# curve stored in A2lCurve class
class A2lCurve():
    def __init__(self, name, desc, data, char_type):
        """
        name: 标定量的名字
        desc: 标定量的描述
        data: 标定量的值
        """
        self.name = name
        self.desc = desc
        self.data = data
        self.char_type = char_type
        self.get()
        
    def get(self):
        self.axisX = self.data["axisX"]["value"]
        self.inputqX = self.data["axisX"]["inputquantity"]
        self.unitX = self.data["axisX"]["unit"]
        self.value = self.data["data"]["value"]
        
        assert isinstance(self.value, list), "value must be a list"
        assert len(self.value) == len(self.axisX), "axisX must be same length as value"
        
    def lookup(self, x):
        if x <= self.axisX[0]:
            return self.value[0]
    
        sz = len(self.axisX)
        if x >= self.axisX[sz-1]:
            return self.value[sz-1]
    
        i = 1
        while x<=self.axisX[i] and i<sz-1:
            i += 1
        gap = (x - self.axisX[i-1]) / (self.axisX[i] - self.axisX[i-1])
        
        return self.value[i-1] + gap * (self.value[i] - self.value[i-1])


# In[230]:


# map stored in A2lMap class
class A2lMap():
    def __init__(self, name, desc, data, char_type):
        self.name = name
        self.desc = desc
        self.data = data
        self.char_type = char_type
        self.axisX = None
        self.axisY = None
        self.value = None
        self.get()
    
    def get(self):
        """
        解析MAP
        """
        self.axisX = [float(v) for v in self.data["axisX"]["value"]]
        self.inputqX = self.data["axisX"]["inputquantity"]
        self.unitX = self.data["axisX"]["unit"]
        
        self.axisY = [float(v) for v in self.data["axisY"]["value"]]
        self.inputqY = self.data["axisY"]["inputquantity"]
        self.unitY = self.data["axisY"]["unit"]
        
        x_size = len(self.axisX)
        y_size = len(self.axisY)
            
        readway = self.data["data"]["readway"]
        rows = [None for i in range(y_size)]
        if readway == "ROW_DIR":
            for idx in range(y_size):
                row = self.data["data"]["value"][idx*x_size : (idx+1)*x_size]
                rows[idx] = row  
            rows = np.array(rows).T
        self.value = rows
        
        row_number = self.value.shape[0]
        col_number = self.value.shape[1]
        
        assert isinstance(self.axisX, list), "axisX must be a list"
        assert isinstance(self.axisY, list), "axisY must be a list"
        assert len(self.axisX) == row_number, "axisX length not match"
        assert len(self.axisY) == col_number, "axisY length not match"
        print(self.axisX)
        print(self.axisY)
        
        f = interpolate.interp2d(self.axisY, self.axisX, self.value.tolist(), kind='linear')
        self.f = f
    def lookup_scipy(self, x, y):
        new_ = self.f(y, x)
        return new_
    
    def lookup_v2(self, x, y):
        """
        根据标定量MAP的两个坐标轴的输入，通过双线性插值的方法获取对应的值
        """
        x_sz = len(self.axisX)
        y_sz = len(self.axisY)
        x_idx = 0
        y_idx = 0
        x_idx_before = x_idx
        y_idx_before = y_idx
        # searach axisX
        if x <= self.axisX[0]:
            x_idx = 0
            x = self.axisX[0]
        elif x>= self.axisX[x_sz-1]:
            x_idx = x_sz - 1
            x = self.axisX[x_sz-1]
        else:
            while x >= self.axisX[x_idx] and x_idx < x_sz:
                if x == self.axisX[x_idx]:
                    break
                x_idx += 1
        # search axisY
        if y <= self.axisY[0]:
            y = self.axisY[0]
            y_idx = 0
        elif y>= self.axisY[y_sz-1]:
            y = self.axisY[y_sz-1]
            y_idx = y_sz - 1
        else:
            while y >= self.axisY[y_idx] and y_idx < y_sz:
                if y == self.axisY[y_idx]:
                    break
                y_idx += 1
        
        print("###")
        print(x_idx, y_idx)  
        
        #print("x_idx: %d, y_idx: %d" % (x_idx, y_idx))
        if x_idx == 0 :
            x_idx_before = x_idx
        else:
            x_idx_before = x_idx - 1
        
        if y_idx == 0 :
            y_idx_before = y_idx
        else:
            y_idx_before = y_idx - 1
          
        print(x_idx_before, y_idx_before)

        Q11 = self.value[x_idx][y_idx_before]
        Q21 = self.value[x_idx][y_idx]
        Q12 = self.value[x_idx_before][y_idx_before]
        Q22 = self.value[x_idx_before][y_idx]
        
        #print("Q11: %f, Q21: %f, Q12: %f, Q22: %f"%(Q11, Q21, Q12, Q22))

        y_gap = self.axisY[y_idx] - self.axisY[y_idx_before]
        x_gap = self.axisX[x_idx] - self.axisX[x_idx_before]
        
        if x_gap == 0 and y_gap == 0:
            return Q11
        elif x_gap == 0 and y_gap != 0:
            percent = (y - self.axisY[y_idx_before]) / y_gap 
            return (Q21 - Q11) * percent + Q11
        elif y_gap == 0 and x_gap != 0:
            percent = (x - self.axisX[x_idx_before]) / x_gap
            return  (Q11 - Q12) * percent + Q12
        else:
            delta1 = Q21 - Q22
            delta2 = Q11 - Q12
            
            tmp1 = delta1 * (x - self.axisX[x_idx_before]) / x_gap + Q22
            tmp2 = delta2 * (x - self.axisX[x_idx_before]) / x_gap + Q12
            tmp = (tmp2 - tmp1) * (y - self.axisY[y_idx_before]) / y_gap + tmp1
            return tmp

    def lookup(self, x, y):
        """
        根据标定量MAP的两个坐标轴的输入，通过双线性插值的方法获取对应的值
        """
        x_sz = len(self.axisX)
        y_sz = len(self.axisY)
        x_idx = 0
        y_idx = 0
        x_idx_before = x_idx
        y_idx_before = y_idx
        # searach axisX
        if x <= self.axisX[0]:
            x_idx = 0
            x = self.axisX[0]
        elif x>= self.axisX[x_sz-1]:
            x_idx = x_sz - 1
            x = self.axisX[x_sz-1]
        else:
            while x >= self.axisX[x_idx] and x_idx < x_sz:
                if x == self.axisX[x_idx]:
                    break
                x_idx += 1
        # search axisY
        if y <= self.axisY[0]:
            y = self.axisY[0]
            y_idx = 0
        elif y>= self.axisY[y_sz-1]:
            y = self.axisY[y_sz-1]
            y_idx = y_sz - 1
        else:
            while y >= self.axisY[y_idx] and y_idx < y_sz:
                if y == self.axisY[y_idx]:
                    break
                y_idx += 1
        
        print("###")
        print(x_idx, y_idx)  
        
        # 4个角的边界条件
#         if x_idx >= x_sz - 1 and y_idx >= y_sz - 1:
#             return self.value[x_sz-1][y_sz-1]
#         if x_idx <= 0 and y_idx <= 0:
#             return self.value[0][0]
#         if x_idx <= 0 and y_idx >= y_sz - 1:
#             return self.value[0][y_sz-1]
#         if x_idx >= x_sz -1 and y_idx <= 0:
#             return self.value[x_sz-1][0]
        
        #print("x_idx: %d, y_idx: %d" % (x_idx, y_idx))
        if x_idx == 0 :
            x_idx_before = x_idx
        else:
            x_idx_before = x_idx - 1
        
        if y_idx == 0 :
            y_idx_before = y_idx
        else:
            y_idx_before = y_idx - 1
          
        print(x_idx_before, y_idx_before)

        Q11 = self.value[x_idx][y_idx_before]
        Q21 = self.value[x_idx][y_idx]
        Q12 = self.value[x_idx_before][y_idx_before]
        Q22 = self.value[x_idx_before][y_idx]
        
        #print("Q11: %f, Q21: %f, Q12: %f, Q22: %f"%(Q11, Q21, Q12, Q22))

        y_gap = self.axisY[y_idx] - self.axisY[y_idx_before]
        x_gap = self.axisX[x_idx] - self.axisX[x_idx_before]
        
        if x_gap == 0 and y_gap == 0:
            return Q11
        
        if x_gap == 0 and y_gap != 0:
            percent = (y - self.axisY[y_idx_before]) / y_gap 
            return (Q21 - Q11) * percent + Q11
        
        if y_gap == 0 and x_gap != 0:
            percent = (x - self.axisX[x_idx_before]) / x_gap
            return  (Q11 - Q12) * percent + Q12
        
        # Y 方向插值
        f1 = (y - self.axisY[y_idx_before]) / y_gap * (Q21 - Q11) + Q11
        f2 = (y - self.axisY[y_idx_before]) / y_gap * (Q22 - Q12) + Q12
        
        # X 方向插值
        fp = (x - self.axisX[x_idx_before]) / x_gap * (f1 - f2) + f2
        
        return fp


# In[231]:


file_name = "C:/Users/GW00205581/Desktop/calibration-data-v1/20191101--k7--5160/parse_calibration.json"
r = {}
with open(file_name, "r") as f:
    for line in f:
        line = line.strip()
        js = json.loads(line)
        label = js["label"]
        if label != "ACM_BOOST_DMND_DEC_RAMP_DOWN_APM":
            continue
        else:
            data = eval(js["data"])
            a2l = A2lMap("", "", data, "")
            print (a2l.value)
            print(a2l.lookup(2.3, 400.0))
            #print(a2l.lookup_scipy(2.0, 4600.0))
            break


# In[232]:


check_label = "ACM_EGRL_NC_PIPE1_HT_EFF_APM"


# In[235]:


file_name = "C:/Users/GW00205581/Desktop/calibration-data-v1/20191101--k7--5160/parse_calibration.json"
r = {}
with open(file_name, "r") as f:
    for line in f:
        line = line.strip()
        js = json.loads(line)
        label = js["label"]
        if label != check_label:
            continue
        else:
            data = eval(js["data"])
            a2l = A2lMap("", "", data, "")
            
            # 4个角
#             print("%.6f"%(a2l.lookup(-50, 5)))
#             print("%.6f"%(a2l.lookup(1000, 5)))
#             print("%.6f"%(a2l.lookup(-50, 160)))
#             print("%.6f"%(a2l.lookup(1000, 160)))
            # 4 条边
#             print( "%.6f"%(a2l.lookup(1050, 6)) )
#             print( "%.6f"%(a2l.lookup(1000, 7)) )
#             print( "%.6f"%(a2l.lookup(1000, 25)) )
#             print( "%.6f"%(a2l.lookup(3089.7, 160)) )
            
            # 准确找到的值
            a = [[-50,5],[1000, 160], [0, 5.5], [100, 20], [1000, 160], [800, 160],[800,130],
                [1000,5],[800,5.5],[-50,160]]
            for tu in a:
                print( "%.2f \t %.2f \t %.6f"%(tu[0], tu[1], a2l.lookup(tu[0],tu[1])) )
            
            print("~" * 50)
            
            # 角
            a = [[-50,5], [-50,160], [1000, 5], [1000,160]]
            for tu in a:
                print( "%.2f \t %.2f \t %.6f"%(tu[0], tu[1], a2l.lookup(tu[0],tu[1])) )
            
            print("~" * 50)
            
            # 边
            a = [[-50,5.25], [-50,40],
                 [1000, 5.25], [1000,145]]
            for tu in a:
                print( "%.2f \t %.2f \t %.6f"%(tu[0], tu[1], a2l.lookup(tu[0],tu[1])) )
            a =[[-25, 5], [450,5],[900,5],
                [-25, 160], [150,160], [900,160]]
            for tu in a:
                print( "%.2f \t %.2f \t %.6f"%(tu[0], tu[1], a2l.lookup(tu[0],tu[1])) )
            
            print("&" * 50)
            print("&" * 50)
            
            a =[[-25, 5.25], [450,8.5],[900,60],
                [566, 78], [-32,123], [755,7.6],
               [-32, 11.4]]
            for tu in a:
                print("QQQQ"*10)
                print( "QQQQ %.2f \t %.2f \t %.6f"%(tu[0], tu[1], a2l.lookup(tu[0],tu[1])) )
                print( "QQQQ %.2f \t %.2f \t %.6f"%(tu[0], tu[1], a2l.lookup_v2(tu[0],tu[1])) )
            #print(a2l.lookup_scipy(2.0, 4600.0))
            break


# In[208]:


(0.59555 + )/2


# In[209]:


(0.579255 - 0.59555) * 0.25 + 0.59555


# In[211]:


(0.3662678 - 0.362433 ) / 0.362433


# In[213]:


0.0047 / 0.515060


# In[ ]:




