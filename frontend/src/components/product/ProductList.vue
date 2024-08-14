<template>
  <VSheet class="d-flex flex-column">
    <v-toolbar v-if="props.toolbar"></v-toolbar>

    <VSheet
      v-if="props.type === 'card'"
      class="d-flex flex-row flex-wrap justify-center "
    >
      <ProductCard
        v-for="product in products"
        :key="product.id"
        :product="product"
        @on-click="cardClick"
      />
    </VSheet>

    <VSheet v-if="props.type === 'row'" class="d-flex flex-column ">
      <ProductRow
        v-for="product in products"
        :key="product.id"
        :product="product"
        @on-click="cardClick"
      />
    </VSheet>

    <VSheet v-if="totalPages > 1">
      <VPagination
        :length="totalPages"
        :total-visible="7"
        v-model="page"
      />
    </VSheet>
  </VSheet>
</template>

<script setup>
import { onMounted, computed, ref, watch } from 'vue';
import { useProductStore } from '@/stores/productStore';
import ProductCard from "@/components/product/ProductCard.vue";
import ProductRow from "@/components/product/ProductRow.vue";
const productStore = useProductStore();
const props = defineProps({
  "type": {
    type: String,
    default: "card"
  },
  "toolbar": {
    type: Boolean,
    default: false
  }
})
const emit = defineEmits(['on-card-click']);
const page = ref(1)

const products = computed(() => {
  return productStore.products;
})

const totalPages = computed(() => {
  return productStore.totalPages;
})

function cardClick(id) {
  emit('on-card-click', id)
}

watch(page, () => {
  productStore.turnPage(page.value)
})


onMounted(async() => {
  productStore.resetStore();
  productStore.fetchProducts();
})
</script>
