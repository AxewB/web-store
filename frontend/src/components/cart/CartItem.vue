<template>
  <v-sheet class="d-flex flex-row pa-2" rounded>
    <v-avatar 
    class="cursor-pointer"
      :image="thumbnail" 
      size="100" 
      rounded 
      color="grey"
      @click="moveToProductPage()
    "/>
    <v-sheet class="flex-grow-1 d-flex flex-column justify-space-between ml-2">
      <v-sheet 
        class="d-flex flex-row align-center text-h6 font-weight-medium cursor-pointer " 
        @click="moveToProductPage()"
      >
        {{ name }}
        <v-sheet class="text-body-1 font-weight-black px-2 py-1 ml-2 bg-success" rounded >
          {{ cost }}$
        </v-sheet>
  
      </v-sheet>
      <v-btn 
      icon="mdi-delete" 
      variant="tonal"
      color="error"
      rounded
      size="small"
      @click="removeFromCart()"
    />
    </v-sheet>
  </v-sheet>
</template>

<script setup>
import { computed } from 'vue';
import { useRouter } from 'vue-router';
import { useCartStore } from '@/stores/cartStore';
const router = useRouter();
const cartStore = useCartStore();
const props = defineProps({
  product: {
    type: Object,
    required: true
  }
})

const thumbnail = computed(() => props.product.thumbnail)
const name = computed(() => props.product.name)
const cost = computed(() => props.product.cost)
const quantity = computed(() => props.product.quantity)

const productId = computed(() => props.product.id)

function removeFromCart() {
  cartStore.removeFromCartById(productId.value)
}

function moveToProductPage() {
  router.push({name: 'productPage', params: {id: productId.value}})
}

</script>
