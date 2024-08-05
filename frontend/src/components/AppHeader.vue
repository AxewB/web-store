<template>
  <v-sheet width="100%" class="bg-transparent">
    <v-toolbar density="default" class="d-flex align-center pa-2 rounded" width="100%">
      <ShopLogo />

      <v-btn
        prepend-icon="mdi-menu"
        size="large"
        variant="flat"
        class="mx-4"
        color="primary"
        @click="moveToCataloguePage()"
      >
        Каталог
      </v-btn>
      <v-text-field
        label="Search"
        hide-details
        variant="solo"
        class="mx-2"
        v-model="searchValue"
      >
        <template #append-inner>
          <v-btn
            color="primary"
            append-icon="mdi-magnify"
            variant="tonal"
            @click="search(searchValue)"
          >
            ИСКАТЬ
          </v-btn>
        </template>
      </v-text-field>

      <v-btn
        class="mx-2 text-caption"
        size="small"
        prepend-icon="mdi-account"
        stacked
        :to="{ name: 'userProfile', query: { tab: 'account' }  }"
        :active="false"
      >
        Профиль
      </v-btn>
      <v-btn
        class="mx-2 text-caption"
        size="small"
        prepend-icon="mdi-package-variant"
        stacked
        :to="{ path: '/user/profile', query: { tab: 'orders' } }"
        :active="false"
      >
        Заказы
      </v-btn>
      <v-btn
        class="mx-2 text-caption"
        size="small"
        prepend-icon="mdi-cart"
        stacked
        :to="{ path: '/cart' }"
        :active="false"
      >
        Корзина
      </v-btn>
    </v-toolbar>
  </v-sheet>
</template>

<script setup>
import { ref } from 'vue';
import ShopLogo from './ShopLogo.vue';
import { useRouter } from 'vue-router';

const emit = defineEmits(['on-search-click'])

const onSearchClick = () => {
  router.push({name: "productSearch"})
}

const router = useRouter();
const searchValue = ref('');

function search(value) {
  router.push({name: "productSearch", query: {search: value}})
  emit('on-search-click')
}

function moveToCataloguePage() {
  router.push({name: "productCatalogue"})
}
</script>
