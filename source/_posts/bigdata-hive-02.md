---
  title: Hive中的默认分隔符及其表示
  date: 2018-12-18 11:20:00
  categories: 大数据
  tags: [Hive]
  description: Hive中的默认分隔符及其表示
---

```
\n    每行一条记录
^A    分隔列（八进制 \001）
^B    分隔ARRAY或者STRUCT中的元素，或者MAP中多个键值对之间分隔（八进制 \002）
^C    分隔MAP中键值对的“键”和“值”（八进制 \003）
```
