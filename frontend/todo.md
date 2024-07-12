# There will be all the todo's

## Pages

### Home page

### 

### Cart page

- [ ] List of all items in the cart

- [ ] Ability to select certain elements in the cart
  
  - [ ] Button to select/deselect all

- [ ] Button to delete everything in the cart

- [ ] Button to go to checkout page



---

## Components

### Header

- [ ] Go to home page button

- [ ] Register / Log in button

- [ ] Go to cart button

### Product card

- [ ] Image

- [ ] Name

- [ ] Quantity (should be displayed as text based on actual quantity in the DB)

## Stores

### User store

- [ ] First name

- [ ] Last name

- [ ] E-mail

- [ ] Cart info

- [ ]  (maybe some more info)

### Product store

- [ ] array with all products



## Database

### Tables

#### user

Fields:

- name

- email

- others...

#### cart

Fields: 

- user id

- product id

#### products

type: table

fields: 

- id

- name

- quantity

category will be result of query to another table

#### categories-products

type: table

fields: 

- id category

- id product

#### categories

type: enum

fields: 

- id

- name