import { defineConfig } from 'vite'
import { svelte } from '@sveltejs/vite-plugin-svelte'
import { vitePreprocess } from '@sveltejs/vite-plugin-svelte'
// import autoPreprocess from "svelte-preprocess";
// import postCssConfig from './postcss.config.cjs'
import tailwind from 'tailwindcss'
import postcssImport from 'postcss-import'
import autoprefixer from 'autoprefixer'
import cssnano from 'cssnano'

export default defineConfig(({ mode }) => {
  const production = mode === 'production'

  return {
    server: {
      port: 5000,
      proxy: {
        '/api': {
          target: 'https://localhost:8443',
          changeOrigin: true,
          secure: false,
          rewrite: path => path.replace(/^\/api/, ''),
        },
      },
    },
    mode,
    root: 'svelte-client',
    publicDir: './public',
    build: {
      outDir: '../src/main/resources/static',
      emptyOutDir: true,
      // rollupOptions: {
      //   input: 'svelte-client/main.js',
      //   output: {
      //       // name: 'app',
      //       // dir: 'hey',
      //       file: 'hey/bundle.js',
      //       // format: 'esm',
      //       // inlineDynamicImports: true
      //   }
      // },
    },
    // optimizeDeps: {
    //     exclude: ['@roxi/routify']
    // },
    plugins: [
      svelte({
        compilerOptions: {
          dev: !production,
        },
        preprocess: [
          // autoPreprocess({
          //     postcss: {
          //         plugins: [
          //             tailwind(),
          //             postcssImport(),
          //             ...(production ? [autoprefixer(), cssnano({ preset: 'default'})] : [])
          //         ]
          //     },
          //     defaults: {style: 'postcss'},
          //     scss: {includePaths: ['node_modules']}
          // })
          vitePreprocess(),
        ],
        include: ['**/*.svelte', '**/*.html'],
      }),
    ],
  }
})
