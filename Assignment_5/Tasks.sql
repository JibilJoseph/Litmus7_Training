-- Task 1: Inner Join

-- * Retrieve a list of all products along with their category names.
-- * Include only products that belong to a category.

select product_id,product_name,category_name from products inner join categories on products.category_id=categories.category_id;


-- Task 2: Left Join

-- * Retrieve all products along with their supplier names.
-- * Include products even if they don’t have a supplier assigned.

select product_name,supplier_name from products left join suppliers on products.supplier_id=suppliers.supplier_id;


-- Task 3: Right Join

-- * Retrieve all suppliers and the products they supply.
-- * Include suppliers even if they currently supply no products.

select supplier_name,product_name from products right join suppliers on products.supplier_id=suppliers.supplier_id;

-- Task 4: Full Outer Join (Emulated in MySQL)

-- * Retrieve all products and all suppliers in a single list, showing which products are supplied by which suppliers.
-- * Include products with no suppliers and suppliers with no products.

select product_name,supplier_name from products left join suppliers on products.supplier_id=suppliers.supplier_id
union
select product_name,supplier_name from products right join suppliers on products.supplier_id=suppliers.supplier_id;

-- Task 5: Join with Inventory

-- * Retrieve product name, supplier name, and current stock quantity.
-- * Include only products that are in stock.

select p.product_name,i.quantity,s.supplier_name from inventory i left join products p on p.product_id=i.product_id 
left join suppliers s on p.supplier_id=s.supplier_id;


-- Task 6: Join with Orders

-- * Retrieve product name, total quantity ordered, and total revenue per product.
-- * Sort the result by total revenue descending.

select 
	p.product_name,
    sum(o.quantity_ordered) as total_quantity_ordered,
    sum(o.total_price) as total_price
from orders o
join products p
on o.product_id=p.product_id
group by p.product_name
order by total_price desc;


-- Task 7: Multi-Table Join

-- * Retrieve order details: order\_id, order\_date, product\_name, category\_name, supplier\_name, quantity\_ordered, total\_price.
-- * Include all orders in the result.

select o.order_id,o.order_date,p.product_name,c.category_name,s.supplier_name,o.quantity_ordered,o.total_price from orders o left join products p on o.product_id=p.product_id left join 
categories c on p.category_id=c.category_id left join suppliers s on p.supplier_id=s.supplier_id;

-- Task 8: Conditional Join

-- * Retrieve product name, supplier name, and quantity in stock for products with less than 10 items in inventory.

select p.product_name,i.quantity,s.supplier_name from inventory i left join products p on i.product_id=p.product_id left join suppliers s on s.supplier_id=p.supplier_id  
where quantity<80;

-- Task 9:

-- * Find suppliers who supply products in multiple categories.
-- * Find products that have never been ordered.
-- * Find the category with the highest total sales.

select s.supplier_id,s.supplier_name,count(distinct p.category_id) as category_count
from suppliers s join products p on s.supplier_id=p.supplier_id
group by s.supplier_id
having category_count>1;

select product_id from products where product_id not in (select product_id from orders);

select p.category_id,sum(o.total_price) as total_sales from orders o left join products p on o.product_id=p.product_id 
group by p.category_id order by total_sales desc limit 1 ;


