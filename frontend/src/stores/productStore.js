import {defineStore} from 'pinia';
import axios from 'axios';
import { useCategoryStore } from './categoryStore';
const API_URL = "http://localhost:8080/api/products";
// const API_URL = 'https://dummyjson.com/products';
export const useProductStore = defineStore('products', {
  state: () => ({
    products: [],
    total: 0,
    skip: 0,
    limit: 10,
    page: 1,
  }),
  getters: {
    totalPages: (state) => Math.ceil(state.total / state.limit),
  },
  actions: {
    async getProducts() {
      try {
        const res = await axios.get(API_URL + `?skip=${this.skip}&limit=${this.limit}`).then((response) => response.data);
        this.products = res.data
        this.total = res.total
        this.skip = res.skip
        this.limit = res.limit
      } catch (error) {
        console.log("error in fetching products");
      }
    },
    async getProduct(id) {
      try {
        const res = await axios.get(`${API_URL}/${id}`).then((response) => response.data);
        return res
      } catch (error) {
        console.log("error in fetching product");
      }
    },

    async addProduct(product) {
      const categoryStore = useCategoryStore();
      categoryStore.getCategories();
      const categories = categoryStore.categories.filter((cat) => product.categories.includes(cat.id));

      axios.post(API_URL, {
        "name": product.name,
        "description": product.description,
        "images": product.images,
        "thumbnail": product.thumbnail,
        "quantity": product.quantity,
        "cost": product.cost,
        "categories": categories
      },
      {
        headers: {
          'Content-Type': 'application/json',
        }
      })
      .then(function (response) {
        console.log(response);
      })
      .catch(function (error) {
        console.log(error);
      });
      this.getProducts();
    },

    async updateProduct(id, product) {
      const categoryStore = useCategoryStore();
      categoryStore.getCategories();
      const categories = categoryStore.categories.filter((cat) => product.categories.includes(cat.id));

      axios.put(API_URL + `/${id}`, {
        "name": product.name,
        "description": product.description,
        "images": product.images,
        "thumbnail": product.thumbnail,
        "quantity": product.quantity,
        "cost": product.cost,
        "categories": categories
    },
    {
      headers: {
        'Content-Type': 'application/json',
      }
    })
      .then(function (response) {
        console.log(response);
      })
      .catch(function (error) {
        console.log(error);
      });
      this.getProducts();
    },
    async deleteProduct(id) {
      axios.delete(API_URL + `/${id}`)
      .then(function (response) {
        console.log(response);

      })
      .catch(function (error) {
        console.log(error);
        });
      this.getProducts();
    },


    async turnPage(page) {
      this.page = page
      this.skip = (this.page - 1) * this.limit
      console.log(this.skip)
      await this.getProducts()
    },
    resetStore() { 
      this.products = []
      this.total = 0
      this.skip = 0
      this.limit = 10
      this.page = 1
    },
  }
})