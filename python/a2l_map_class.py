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
            print("AAAAAA")
            delta1 = Q11 - Q12
            delta2 = Q21 - Q22
            
            tmp1 = delta1 * (x - self.axisX[x_idx_before]) / x_gap + Q12
            tmp2 = delta2 * (x - self.axisX[x_idx_before]) / x_gap + Q22
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

