// import '@/assets/style.css'
// import '@/assets/reset.less'
import App from './App.vue'
import router from './router'
// import 'ant-design-vue/dist/antd.css'
import '@/router/auth'
const pinia = createPinia()

const app = createApp(App)

app.use(router).use(pinia)

app.mount('#app')
