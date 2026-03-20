[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/j2P4pQ_d)
# **Web应用开发作业01：Servlet 外卖订单系统**

## **目标**

通过 Servlet 处理不同类型的请求参数，实现一个简单的 **外卖订单管理系统**，包括菜单查询、订单创建和订单详情查看，从而理解 Web 应用中的 **Query Parameter、Form Parameter 和 Path Parameter** 的使用。

---

# **任务描述**

## **1. 搭建基本项目**

这是一个标准 Maven Web 项目（WAR），请按以下结构组织代码与静态资源：

```
src/
  main/
    java/
      cs/sbs/web/
        servlet/
          MenuListServlet.java
          OrderCreateServlet.java
          OrderDetailServlet.java
        model/
          MenuItem.java
          Order.java
    webapp/
      order.html
      WEB-INF/
        web.xml
  test/
    run_all_tests.sh
    test*.sh
```

其中：
- Servlet 映射在 `src/main/webapp/WEB-INF/web.xml` 中配置
- 首页为 `src/main/webapp/order.html`（作为 welcome file）

## **2. 本地运行方式（与自动评分一致）**

要求：JDK 17 + Maven

启动：

```bash
mvn -q jetty:run
```

服务默认端口为 8080，浏览器打开：

```
http://localhost:8080/
```

自动评分脚本（需要先启动服务）：

```bash
bash src/test/run_all_tests.sh
```

---

# **查询菜单 (Query Parameter)**

编写 `MenuListServlet`，处理 **GET 请求**，返回外卖菜单，并支持按菜名搜索。

### URL 示例

查看所有菜单

```
http://localhost:8080/menu
```

按菜名搜索

```
http://localhost:8080/menu?name=Fried
```

### 返回示例

```
Menu List:

1. Fried Rice - $8
2. Fried Noodles - $9
3. Burger - $10
```

---

# **创建订单 (Form Parameter)**

编写 `OrderCreateServlet`，处理 **POST 请求**，提交新的订单信息。

前端页面：`src/main/webapp/order.html`

```html
<h2>Create Food Order</h2>

<form action="/order" method="post">
    Customer Name: <input type="text" name="customer"><br>
    Food Name: <input type="text" name="food"><br>
    Quantity: <input type="number" name="quantity"><br>
    <input type="submit" value="Place Order">
</form>
```

### Servlet 处理

Servlet 接收表单参数：

* `customer`
* `food`
* `quantity`

并返回一段可读的文本结果（建议 `text/plain; charset=UTF-8`），包含订单创建是否成功及必要字段（如订单 ID）。

示例：

```
Order Created: 1001
```

---

# **查看订单详情 (Path Parameter)**

编写 `OrderDetailServlet`，通过 **路径参数**查看指定订单详情。

### URL 示例

```
http://localhost:8080/order/1001
```

其中 `1001` 为订单 ID。

### 返回示例

```
Order Detail

Order ID: 1001
Customer: Alice
Food: Fried Rice
Quantity: 2
```

---

# **作业要求**

## **1. 功能完整**

需要实现以下三个功能：

* 菜单查询
* 创建订单
* 查看订单详情

---

## **2. 页面交互**

设计简单 HTML 页面，支持：

* 表单提交
* 通过超链接访问订单详情

示例：

```
Order #1001 (Click to view details)
```

---

## **3. 数据模拟**

无需使用数据库，可以使用：

```java
ArrayList<Menu>
ArrayList<Order>
```

模拟数据存储。

例如：

菜单：

```
Fried Rice - $8
Fried Noodles - $9
Burger - $10
```

订单：

```
OrderID:1001 Alice Fried Rice x2
```

---

## **4. 异常处理**

需要处理以下情况：

* 表单参数为空
* 数量不是合法数字
* 查询不到订单

示例：

```
Error: quantity must be a valid number
```

---

# **提交方式**

提交内容包括：

1️⃣ **完整项目代码**

2️⃣ **运行截图（放在 src/main/resources）**

例如：

* 菜单列表页面
* 创建订单页面
* 订单详情页面

3️⃣ **运行方式**

可以使用以下方式测试：

* Jetty 插件（自动评分以此为准）
* 本地 Tomcat
* IDE 内置 Web 服务器

---

# **自动评分说明**

仓库集成了自动评分：
- 工作流会启动 Jetty（8080 端口）并运行 `src/test/run_all_tests.sh`
- 请确保接口路径、返回内容与用例预期一致（尤其是 `/menu`、`/order`、`/order/{id}`）
- 若使用Github提交项目，则可自动开启评分流程
