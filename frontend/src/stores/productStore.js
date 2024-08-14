import {defineStore} from 'pinia';
import axios from 'axios';
import { useCategoryStore } from '@/stores/categoryStore';
import authHeader from "@/services/auth-header";
import contentHeader from '@/services/content-header';
import ResponseHandler from '@/scripts/responseHandler';

const API_URL = "http://localhost:8080/api/products";

export const useProductStore = defineStore('products', {
  state: () => ({
    products: [], // Список продуктов
    total: 0,     // Общее количество продуктов
    skip: 0,      // Количество пропускаемых продуктов
    limit: 10,    // Ограниечение на количество получаемых продуктов
    page: 1,      // Страница
  }),
  getters: {
    totalPages: (state) => Math.ceil(state.total / state.limit),
  },
  actions: {
    /**
     * Запрос на получение продуктов с сервера
     * @returns {Array} - Результат запроса вида [ответ?, ошибка?]. Ошибка равна null, если запрос прошел успешно. Иначе наоборот
     */
    async fetchProducts() {
      return axios.get(API_URL + `?skip=${this.skip}&limit=${this.limit}`)
      .then(response => {
        this.setFetchedParams(response.data)
        return ResponseHandler.success(response, "Продукты получены")
      })
      .catch(error => {
        return ResponseHandler.error(error, "Произошла ошибка при получении продуктов")
      });
    },
   
    /**
     * Запрос на получение продуктов согласно поисковому запросу
     * @param {String} name - Строка, по которой будет проходить поиск
     * @returns {Array} - Результат запроса вида [ответ?, ошибка?]. Ошибка равна null, если запрос прошел успешно. Иначе наоборот
     */
    async fetchProductsByName(name) {
      return axios.get(API_URL + `/search?name=${name}&skip=${this.skip}&limit=${this.limit}`)
      .then(response => {
        this.setFetchedParams(response.data)
        return ResponseHandler.success(response, "Продукты получены")
      })
      .catch(error => {
        return ResponseHandler.error(error, "Произошла ошибка при получении продуктов")
      });
    },
    /**
     * Запрос на получение продуктов согласно категории
     * @param {Number} categoryId - id категории
     * @returns {Array} - Результат запроса вида [ответ?, ошибка?]. Ошибка равна null, если запрос прошел успешно. Иначе наоборот
     */
    async fetchProductByCategory(categoryId) {
      return axios.get(API_URL + `/category/${categoryId}?skip=${this.skip}&limit=${this.limit}`)
      .then(response => {
        this.setFetchedParams(response.data)
        return ResponseHandler.success(response, "Продукты получены")
      })
      .catch(error => {
        return ResponseHandler.error(error, "Произошла ошибка при получении продуктов по категории")
      });
    },
    /**
     * Запрос на получение продукта по id
     * @param {Number} id - id продукта
     * @returns {Array} - Результат запроса вида [ответ?, ошибка?]. Ответ содержит объект продукта. Ошибка равна null, если запрос прошел успешно. Иначе наоборот
     */
    async fetchProduct(id) {
      return axios.get(`${API_URL}/${id}`)
      .then((response) => {
        return ResponseHandler.success(response, "Продукт получен")
      })
      .catch((error) => {
        return ResponseHandler.error(error, "Произошла ошибка при получении продукта")
      })
    },

    /**
     * Запрос на добавление продукта в БД
     * @param {Object} product - Продукт, который нужно добавить
     * @returns {Array} - Результат запроса вида [ответ?, ошибка?]. Ошибка равна null, если запрос прошел успешно. Иначе наоборот
     */
    async addProduct(product) {
      const categoryStore = useCategoryStore();
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

      return axios.post(API_URL + '/add', data, { headers: contentHeader() })
      .then(response => {
        this.fetchProducts();
        return ResponseHandler.success(response, "Продукт успешно добавлен")
      })
      .catch(error => {
        return ResponseHandler.error(error, "Произошла ошибка при добавлении продукта")
      });

    },

    /**
     * Запрос на обновление параметров продукта
     * @param {Number} id - id изменяемого продукта
     * @param {Object} product - новые данные
     * @returns {Array} - Результат запроса вида [ответ?, ошибка?]. Ошибка равна null, если запрос прошел успешно. Иначе наоборот
     */
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

      return axios.put(API_URL + `/${id}`, data, { headers: contentHeader() })
      .then(response => {
        this.fetchProducts();
        return ResponseHandler.success(response, "Продукт успешно обновлен")
      })
      .catch(error => {
        return ResponseHandler.error(error, "Произошла ошибка при обновлении продукта")
      });
    },

    /**
     * Запрос на удаление продукта
     * @param {Number} id - Идентификатор продукта
     * @returns {Array} - Результат запроса вида [ответ?, ошибка?]. Ошибка равна null, если запрос прошел успешно. Иначе наоборот
     */
    async deleteProduct(id) {
      return axios.delete(API_URL + `/${id}`, { headers: authHeader() })
      .then(response => {
        return ResponseHandler.success(response, "Продукт успешно удален")
      })
      .catch(error => {
        return ResponseHandler.error(error, "Произошла ошибка при удалении продукта")
      });

      this.fetchProducts();
    },

    /**
     * Сеттер для параметра skip.
     * @param {Number} skip - Новое значение.
     */
    setSkip(skip) {
      this.skip = skip
    },

    /**
     * Сеттер для параметра limit.
     * @param {Number} limit - Новое значение.
     */
    setLimit(limit) {
      this.limit = limit
    },

    /**
     * Переход на страницу
     * @param {Number} page - Выбранная страница.
     */
    async turnPage(page) {
      this.page = page
      this.skip = (this.page - 1) * this.limit
      this.fetchProducts()
    },
    /**
     * Вспомогательный метод для установки полученных значений.
     * @param {Object} params - Новые значения.
     */
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
    /**
     * Сброс значений хранилища
     */
    resetStore() {
      this.products = []
      this.total = 0
      this.skip = 0
      this.limit = 10
      this.page = 1
    },
  }
})