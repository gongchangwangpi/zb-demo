#-*- coding = utf-8 -*-
# @author zhangbo
# @date 2021/3/28 11:40:36

import xlwt

workbook = xlwt.Workbook(encoding="utf-8")

worksheet = workbook.add_sheet("test")

worksheet.write(0, 0, "hello")
worksheet.write(0, 1, "python")
worksheet.write(0, 2, "excel")

workbook.save("test_xlwt.xls")
