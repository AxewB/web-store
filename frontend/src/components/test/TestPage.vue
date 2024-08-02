<template>
  <div>
    <v-btn color="primary" @click="getData($event)">button</v-btn>
  </div>
</template>

<script setup>
import products from '@/assets/dummyjson_products.json';

function getData(event) {
  event.preventDefault();
  // let result = [];
  let result = '';
  for (let i = 0; i < 92; i++) {
    const product = products.products[i];
    // INSERT INTO product (id, name, description, images, thumbnail, quantity, cost) VALUES
    let images = product.images.map(image => `"${image.replace(/'/g, "''")}"`).join(',');
    result +=`(${product.id + 15}, '${product.title.replace(/'/g, "''")}', '${product.description.replace(/'/g, "''")}', '{${images}}', '${product.thumbnail.replace(/'/g, "''")}', ${product.stock}, ${product.price}), \n`

    // all info 
    // result.push(
    //   {
    //     id: product.id,
    //     name: product.title,
    //     description: product.description,
    //     images: product.images,
    //     thumbnail: product.thumbnail,
    //     quantity: product.stock,
    //     cost: product.price,
    //   }
    // )

    // id + category
    // result.push(
    //   {
    //     id: product.id + 15,
    //     name: product.title,
    //     description: product.description,
    //     category: product.category
    //   }
    // )


    // ids
    // result.push(product.id + 15)
  }

  console.log(result)
  if (true) {
    var element = document.createElement('a');
    // element.setAttribute('href', 'data:text/plain;charset=utf-8,' + encodeURIComponent(JSON.stringify(result)));
    element.setAttribute('href', 'data:text/plain;charset=utf-8,' + encodeURIComponent(result));
    // element.setAttribute('download', "ids.txt"); // 
    element.setAttribute('download', "products_to_sql.txt"); 
    // element.setAttribute('download', "NamesAndCategories.txt");

    element.style.display = 'none';
    document.body.appendChild(element);

    element.click();

    document.body.removeChild(element);
  }
  
}
</script>