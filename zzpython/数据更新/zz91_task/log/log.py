#!/usr/bin/env python
# coding:utf-8

import sys,time

class DisplayFormat(object):

    def format_size(self,size):
        KB = 1024                     # KB -> B  1024
        MB = 1048576                # MB -> B  1024 * 1024
        GB = 1073741824                # GB -> B  1024 * 1024 * 1024
        TB = 1099511627776            # TB -> B  1024 * 1024 * 1024

        if size >= TB:
            size = str(size >> 40) + 'T'
        elif size < KB:
            size = str(size) + 'B'
        elif size >= GB and size < TB:
            size = str(size >> 30) + 'G'
        elif size >= MB and size < GB:
            size = str(size >> 20) + 'M'
        else:
            size = str(size >> 10) + 'K'

        return size

    formatstring = '%-18s %-10s %-12s %8s %10s %10s %10s %10s %10s %10s %10s'

    def echo_line(self):
        '''输出头部横线'''
        print self.formatstring % ('-'*15,'-'*10,'-'*12,'-'*12,'-'*10,'-'*10,'-'*10,'-'*10,'-'*10,'-'*10,'-'*10,)

    def echo_head(self):
        '''输出头部信息'''
        print self.formatstring % ('IP','Traffic','Time','Time%',200,404,403,503,500,302,304)

    def echo_error(self):
        '''输出错误信息'''
        print 'Usage: ' + sys.argv[0] + 'filepath [number]'

    def echo_time(self):
        '''输出脚本执行时间'''
        print 'The script is running %s second' % time.clock()


class HostInfo(object):

    # 定义一个主机ip 的所有状态列表
    host_info = ['200','404','403','503','500','302','304','size','time']

    def __init__(self,host):
        '''初始化一个主机信息字典'''
        self.host = host = {}.fromkeys(self.host_info,0)

    def add_1(self,status_size,is_size):
        '''对访问次数,http返回的状态码，ip流量进行加1操作'''
        if status_size == 'time':
            self.host['time'] += 1
        elif is_size:
            self.host['size'] = self.host['size'] + status_size
        else:
            self.host[status_size] += 1

    def get_value(self,value):
        '''取出字典的值'''
        return self.host[value]


class AnalysisFile(object):

    def __init__(self):
        '''初始化一个空字典'''
        self.empty = {}
        self.total_request_time,self.total_traffic,self.total_200,\
        self.total_404,self.total_403,self.total_503,self.total_500,\
        self.total_302,self.total_304 = 0,0,0,0,0,0,0,0,0

    def split_line_todict(self,line):
        '''传入文件的每一行取出0、8、9字段 生成字典 并返回这个字典'''
        line_split = line.split()
        line_dict = {'remote_host':line_split[0],'status':line_split[8],'bytes_sent':line_split[9]}
        return line_dict

    def read_log(self,logs):
        for line in logs:
            try:
                dict_line = self.split_line_todict(line)
                host = dict_line['remote_host']
                status = dict_line['status']
            except ValueError:
                continue
            except IndexError:
                continue

            if host not in self.empty:
                host_info_obj = HostInfo(host)
                self.empty[host] = host_info_obj
            else:
                host_info_obj = self.empty[host]

            host_info_obj.add_1('time',False)

            if status in host_info_obj.host_info:
                host_info_obj.add_1(status,False)

            try:
                bytes_sent = int(dict_line['bytes_sent'])
            except ValueError:
                bytes_sent = 0

            host_info_obj.add_1(bytes_sent,True)

        return self.empty

    def return_sorted_list(self,true_dict):
        '''循环读取字典,计算总的流量、总的访问次数以及总的http返回码'''
        for host_key in true_dict:
            host_value = true_dict[host_key]
            time = host_value.get_value('time')
            self.total_request_time = self.total_request_time + time
            size = host_value.get_value('size')
            self.total_traffic = self.total_traffic + size

            # 获取http返回状态码的次数
            v_200 = host_value.get_value('200')
            v_404 = host_value.get_value('404')
            v_403 = host_value.get_value('403')
            v_503 = host_value.get_value('503')
            v_500 = host_value.get_value('500')
            v_302 = host_value.get_value('302')
            v_304 = host_value.get_value('304')

            # 重新规划字典
            true_dict[host_key] = {'200':v_200,'404':v_404,'403':v_403,\
                                   '503':v_503,'500':v_500,'302':v_302,\
                                   '304':v_304,'size':size,'time':time}


            # 计算http返回状态码的总量
            self.total_200 = self.total_200 + v_200
            self.total_404 = self.total_404 + v_404
            self.total_403 = self.total_403 + v_403
            self.total_503 = self.total_503 + v_503
            self.total_500 = self.total_500 + v_500
            self.total_302 = self.total_302 + v_302
            self.total_304 = self.total_304 + v_304

                # 对总的访问次数和访问流量进行降序排序,并生成一个有序的列表
        sorted_list = sorted(true_dict.items(),key=lambda i:(i[1]['size'],\
                                                                 i[1]['time']),reverse=True)

        return sorted_list


class Main(object):

    def main(self):
        '''主调函数'''
        # 初始化DisplayFormat类的实例
        displayformat = DisplayFormat()

        args = len(sys.argv)

        if args == 1:
            displayformat.echo_error()
        elif args == 2 or args == 3:
            log_file = sys.argv[1]
            print log_file
            try:
                files = open(log_file,'r')
                if args == 3:
                    lines = int(sys.argv[2])
                else:
                    lines = 0
            except IOError,e:
                print
                print e
                displayformat.echo_error()
            except VaueError,e:
                print
                print e
                displayformat.echo_error()

        else:
            displayformat.echo_error()


        #AnalysisFile类的实例化
        fileanalysis = AnalysisFile()
        # 调用read_log方法
        news_dict = fileanalysis.read_log(files)

        # 调用return_sorted_list方法
        new_list = fileanalysis.return_sorted_list(news_dict)

        # 计算所有ip的总量
        total_ip = len(new_list)

        if lines:
            new_list = new_list[0:lines]
        files.close()

        # 打印出总的ip数,总访问流量,总的访问次数
        print
        total_request_time = fileanalysis.total_request_time
        total_traffic = displayformat.format_size(fileanalysis.total_traffic)
        print '总IP数量: %s    总的访问流量: %s    总的请求次数: %d' % (total_ip,\
                                                                   total_traffic,\
                                                                   total_request_time)
        
        # 打印头部信息,和横线                                                                       
        print 
        displayformat.echo_head()
        displayformat.echo_line()

        # 循环读取news_list列表取出time项目 计算time百分比 通过displayformat格式化输出主机信息
        for i in new_list:
            time = i[1]['time']
            time_percentage = (float(time) / float(fileanalysis.total_request_time)) * 100
            print displayformat.formatstring % (i[0],\
                                                displayformat.format_size(i[1]['size']),\
                                                time,str(time_percentage)[0:5],\
                                                i[1]['200'],i[1]['404'],i[1]['403'],\
                                                i[1]['503'],i[1]['500'],i[1]['302'],i[1]['304'])

        if not lines or total_ip == lines:
            displayformat.echo_line()
            print displayformat.formatstring % (total_ip,total_traffic,total_request_time,'100%',\
                                                fileanalysis.total_200,fileanalysis.total_404,\
                                                fileanalysis.total_403,fileanalysis.total_503,\
                                                fileanalysis.total_500,fileanalysis.total_302,\
                                                fileanalysis.total_304)

        # 显示执行脚本的时间
        print 
        displayformat.echo_time()

if __name__ == '__main__':
    main = Main()
    main.main()