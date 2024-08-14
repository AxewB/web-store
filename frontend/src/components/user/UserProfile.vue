<template>
  <VSheet v-if="currentUser" height="100%" class="d-flex flex-column bg-transparent">
    <AppHeader/>

    <PageHeading title="Профиль"/>

    <VSheet class="d-flex flex-row bg-transparent">
      <VSheet
        class="mr-2 bg-transparent"
        min-width="200px"
      >
        <VList
          class="mb-2"
          v-model="window" 
          rounded
          width="100%"
          elevation="10"
        >
          <VListItem
            prepend-icon="mdi-crown"
            :title="currentUser.username"
          />
        </VList>

        <VList v-model="window"
          nav
          rounded
          density='compact'
          width="100%"
          elevation="10"
        >
          <VListItem
            link
            @click="switchTab('account')"
            :active="tab === 'account'"
            prepend-icon="mdi-account"
            title="Аккаунт"
          />
          <VListItem
            link
            @click="switchTab('orders')"
            :active="tab === 'orders'"
            prepend-icon="mdi-package-variant"
            title="Заказы"
          />
          <VListItem
            link
            @click="switchTab('settings')"
            :active="tab === 'settings'"
            prepend-icon="mdi-cog"
            title="Настройки"
          />
        </VList>
        <VList
          v-if="userStore.user.roles.includes('ROLE_ADMIN')"
          v-model="window"
          class="mt-2"
          nav
          rounded
          density='compact'
          width="100%"
          elevation="10"
        >
          <VListItem
            link
            :to="{ name: 'admin' }"
            prepend-icon="mdi-security"
            title="Админ панель"
          />
        </VList>
        <VList
          class="mt-2 bg-transparent"
          nav
          rounded
          density='compact'
          width="100%"
        >
          <VListItem
            @click="logout"
            color="error"
            width="100%"
            title="Выйти"
            prepend-icon="mdi-logout"
            border
          />
        </VList>
      </VSheet>
      <VSheet class="flex-grow-1 bg-transparent">
        <VSheet
          elevation="3"
          rounded
        >
          <Component :is="currentTab"/>
        </VSheet>
      </VSheet>
    </VSheet>
  </VSheet>
</template>

<script setup>
import AppHeader from '@/components/AppHeader.vue'
import { computed, onMounted, ref, shallowRef, watch } from 'vue';
import { useUserStore } from '@/stores/userStore';
import { useRouter, useRoute } from 'vue-router';
import { UserInfo, UserOrders, UserSettings } from '@/components/user/UserProfileTabs.js';
import PageHeading from '../PageHeading.vue';
const router = useRouter();
const route = useRoute();
const userStore = useUserStore();
const window = shallowRef(UserInfo)
const currentUser = computed(() => {return userStore.user});

const logout = () => {
  userStore.logout();
}

const loggedIn = computed(() => userStore.status.loggedIn)
const tab = computed(() => route.query.tab)
const currentTab = computed(() => {
  switch (tab.value) {
    case 'account': return UserInfo
    case 'orders': return UserOrders
    case 'settings': return UserSettings
    default: return UserInfo
  }
})
watch(tab, (value) => {
  window.value = getWindow(value)
})
function getWindow(tab) {
  switch (tab) {
    case 'account': return UserInfo
    case 'orders': return UserOrders
    case 'settings': return UserSettings
    default: return UserInfo
  }
}
function switchTab(tab) {
  router.push({query: {tab}})
}
onMounted(() => {
  if (!loggedIn.value) {
    router.push('/user/login');
  }
})
</script>