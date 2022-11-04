import App from './App.svelte'

// const loader = () => {
//     document.addEventListener('DOMContentLoaded', function (event) {
//         const app = new App({
//             target: document.getElementById('my-component'),
//             props: {
//                 name: 'Johnyy Boy'
//             }
//         })
//         window.app = app

//     })
// }
// export default loader()

const app = new App({
	target: document.body,
});

export default app;