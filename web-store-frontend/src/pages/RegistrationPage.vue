<template>
	<v-sheet 
		height="100vh" 
		width="100vw"
		class="d-flex 
					justify-center 
					align-center 
					flex-column
					bg-transparent">
			<v-sheet class="pa-4 rounded d-flex flex-column mb-2"
			width="400px">
			<v-progress-linear :model-value="window * 33.3"></v-progress-linear>
			</v-sheet>
			<v-sheet
				class="pa-4 rounded d-flex flex-column"
				width="400px">
				<span class="text-h5 mb-2">
					Регистрация
				</span>
				<v-window v-model="window">
						<v-window-item>
							<v-text-field label="Логин" autofocus/>
						</v-window-item>
						<v-window-item>
							<v-text-field label="Почта"/>
						</v-window-item>
						<v-window-item>
							<v-text-field label="Пароль" type="password"/>
							<v-text-field label="Повторите пароль" type="password"/>
						</v-window-item>
						<v-window-item>
							<v-text-field label="Че-то еще"/>
						</v-window-item>
				</v-window>
				<v-sheet class="d-flex justify-space-between">
						<v-btn 
							:hidden="window === 0"
							@click="window > 0 ? window-- : window"
						>
							Назад
						</v-btn>
						<v-btn 
							@click="window < 3 ? window+=1 : window"
							color="primary">
							{{ window !== 3 ? "Далее" : "Закончить"}}
						</v-btn>
				</v-sheet>
				<!-- <span class="text-h4">
					Login
				</span>
					<divider class="ma-2"/>
				<v-text-field
					label="Login"/>
				<v-text-field
					label="Password"/>
				<v-btn variant="tonal" size="large">Login</v-btn> -->
			</v-sheet>
	</v-sheet>
</template>

<script setup>
import {ref} from 'vue'

const window = ref(0)
</script>
