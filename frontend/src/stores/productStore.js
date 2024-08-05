import {defineStore} from 'pinia';
import axios from 'axios';
import { useCategoryStore } from '@/stores/categoryStore';
import authHeader from "@/services/auth-header";
import contentHeader from '@/services/content-header';

const API_URL = "http://localhost:8080/api/products";

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
    async fetchProducts() {
      return await axios.get(API_URL + `?skip=${this.skip}&limit=${this.limit}`)
      .then(response => {
        this.setFetchedParams(response.data)
      })
      .catch(error => error);
    },
    async fetchProductsByName(name) {
      return await axios.get(API_URL + `/search?name=${name}&skip=${this.skip}&limit=${this.limit}`)
      .then(response => this.setFetchedParams(response.data))
      .catch(error => error);
    },
    async fetchProductByCategory(categoryId) {
      return await axios.get(API_URL + `/category/${categoryId}?skip=${this.skip}&limit=${this.limit}`)
      .then(response => this.setFetchedParams(response.data))
      .catch(error => error);
    },
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
    async getProductsWithParams(skip, limit) {
      try {
        const res = await axios.get(API_URL + `?skip=${skip}&limit=${limit}`).then((response) => response.data);
        return res
      } catch (error) {
        console.log("error in fetching products");
      }
    },
    async getProductsByName(name) {
      try {
        const res = await axios.get(API_URL + `/search/${name}`).then((response) => response.data);
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
      // await categoryStore.getCategories();
      const categories = categoryStore.categories.filter((cat) => product.categories.includes(cat.id));
      const data  = {
        "name": product.name,
        "description": product.description,
        "images": product.images,
        "thumbnail": product.thumbnail,
        "quantity": product.quantity,
        "cost": product.cost,
        "categories": categories
      }

      axios.post(API_URL + '/add', data, { headers: contentHeader() })
      .then(response => console.log(response))
      .catch(error => console.log(error));

      await this.fetchProducts();
    },

    async updateProduct(id, product) {
      const categoryStore = useCategoryStore();
      const categories = categoryStore.categories.filter((cat) => product.categories.includes(cat.id));
      const data = {
        "name": product.name,
        "description": product.description,
        "images": product.images,
        "thumbnail": product.thumbnail,
        "quantity": product.quantity,
        "cost": product.cost,
        "categories": categories
      }

      axios.put(API_URL + `/${id}`, data, { headers: contentHeader() })
        .then(response => {console.log(response)})
        .catch(error => console.log(error));

      await this.fetchProducts();
    },
    async deleteProduct(id) {
      axios.delete(API_URL + `/${id}`, { headers: authHeader() })
        .then(response => console.log(response))
        .catch(error => console.log(error));

      await this.fetchProducts();
    },

    setSkip(skip) {
      this.skip = skip
    },

    setLimit(limit) {
      this.limit = limit
    },

    async turnPage(page) {
      this.page = page
      this.skip = (this.page - 1) * this.limit
      console.log(this.skip)
      await this.fetchProducts()
    },
    setFetchedParams(params) {
      if (params) {
        this.products = params.data;
        this.skip = params.skip;
        this.limit = params.limit;
        this.total = params.total;
        this.page = 1
      }
      else {
        this.resetStore();
      }
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