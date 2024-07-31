import { defineStore } from 'pinia'
import axios from 'axios'
const API_URL = "http://localhost:8080/api/category";
let user = JSON.parse(localStorage.getItem('user'));

export const useCategoryStore = defineStore('categories', {
  state: () => ({
    categories: [],
    category: {},
    categoryPath: []
  }),
  getters: {
    rootCategories(state) {
      return state.categories.filter((cat) => cat.parent === null)
    },
    getCategoriesNames(state) {
      return state.categories.map((cat) => cat.name)
    },
    getCategoriesNamesAndIds(state) {
      return state.categories.map((cat) => ({ name: cat.name, id: cat.id }))
    }
  },
  actions: {
    async getCategories() {
      const res = await axios.get(API_URL).then((response) => response.data);
      this.categories = res;
    },
    async getCategory(id) {
      try {
        const res = await axios.get(API_URL + `/${id}`).then((response) => response.data)
        return res;
      } catch (error) {
        console.log("error fetching categories");
      }
    },
    async getCategoryPath(id) {
      const res = await axios.get(API_URL + `/${id}/path`).then((response) => response.data)
      this.categoryPath = res;
    },

    async addCategory(category) {
      const parent = category.parent ? this.categories.filter((cat) => cat.id === category.parent) : null

      await axios.post(API_URL, {
        "name": category.name,
        "parent": parent
      },
      {
        headers: {
          'Content-Type': 'application/json',
          "Authorization": 'Basic ' + user.accessToken
        },
      })
      .then(response => {
        console.log('response', response)
      })
      .catch(error => {
        console.log('error', error)
      })

      await this.getCategories();
    },

    async updateCategory(id, category) {
      await this.getCategories();
      const parent = category.parent ? this.categories.filter((cat) => cat.id === category.parent) : null

      axios.put(API_URL + `/${id}`, {
        name: category.name,
        parent: parent
      },
      {
        headers: {
          'Content-Type': 'application/json',
          'Authorization': Authorization
        },
      })
      .then(response => {
        console.log(response)
      })
      .catch(error => {
        console.log(error)
      })
    },

    async deleteCategory(id) {
      axios.delete(API_URL + `/${id}`)
      .then(response => {
        console.log(response)
      })
      .catch(error => {
        console.log(error)
      })
    },

    initCategories() {
      this.getCategories();
    },
    initCategoriesForProduct(id) {
      this.getCategory(id);
      this.getCategoryPath(id);
    }
  }
})