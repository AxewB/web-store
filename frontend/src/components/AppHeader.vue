<template>
  <VSheet
    class="bg-transparent"
    width="100%"
  >
    <VToolbar
      class="d-flex align-center pa-2 rounded"
      density="default"
      width="100%"
    >
      <ShopLogo />

      <VBtn
        prepend-icon="mdi-menu"
        size="large"
        variant="flat"
        class="mx-4"
        color="primary"
        @click="moveToCataloguePage()"
      >
        Каталог
      </VBtn>

      <VTextField
        label="Поиск"
        hide-details
        variant="solo"
        class="mx-2"
        v-model="searchValue"
      >
        <template #append-inner>
          <VBtn
            color="primary"
            append-icon="mdi-magnify"
            variant="tonal"
            @click="search(searchValue)"
          >
            ИСКАТЬ
          </VBtn>
        </template>
      </VTextField>

      <VBtn
        class="mx-2 text-caption"
        size="small"
        prepend-icon="mdi-account"
        stacked
        :to="{ name: 'userProfile', query: { tab: 'account' }  }"
        :active="false"
      >
        Профиль
      </VBtn>
      <VBtn
        class="mx-2 text-caption"
        size="small"
        prepend-icon="mdi-package-variant"
        stacked
        :to="{ path: '/user/profile', query: { tab: 'orders' } }"
        :active="false"
      >
        Заказы
      </VBtn>
      <VBtn
        class="mx-2 text-caption"
        size="small"
        prepend-icon="mdi-cart"
        stacked
        :to="{ path: '/cart' }"
        :active="false"
      >
        Корзина
      </VBtn>
    </VToolbar>
  </VSheet>
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
