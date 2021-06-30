import svelte from 'rollup-plugin-svelte'
import postcss from 'rollup-plugin-postcss'
import json from '@rollup/plugin-json'
import resolve from '@rollup/plugin-node-resolve'
import commonjs from '@rollup/plugin-commonjs'
import livereload from 'rollup-plugin-livereload'
import { terser } from 'rollup-plugin-terser'
import replace from '@rollup/plugin-replace';
import routify from '@roxi/routify/plugins/rollup'
import autoPreprocess from 'svelte-preprocess'

const production = !process.env.ROLLUP_WATCH
// process.env.NODE_ENV = production ? 'production' : 'development'

const inputJS = 'src/main/svelte/main.js'
const outputBundleJS = 'src/main/resources/static/bundle.js'
const appName = 'app'

export default {
    input: inputJS,
    output: {
      name: appName,
      file: outputBundleJS,
      format: 'iife',
      inlineDynamicImports: true
      // This will inline dynamic imports instead of creating new chunks to create a single bundle. https://rollupjs.org/guide/en/#outputinlinedynamicimports
      // If we want to split the output into chunks:
      // dir: 'src/main/resources/static/js/',
      // format: 'esm',
      // for performance, disabling filename hashing in development
      // chunkFileNames:`[name]${production && '-[hash]' || ''}.js`
    },
    plugins: [
      routify({
        singleBuild: production
      }),
      postcss({
        plugins: []
      }),
      replace({
        'process.env.NODE_ENV': JSON.stringify(
          production ? 'production' : 'development'
        )
      }),
      svelte({
          dev: !production,
          css: css => css.write('svelte-components.css'),
          preprocess: [
            autoPreprocess({
              postcss: require('./postcss.config.js'),
              defaults: { style: 'postcss' },
              scss: { includePaths: ['node_modules'] }
            })
          ],
          include: ['**/*.svelte', '**/*.html']
          // Emit CSS as "files" for other plugins to process. default is true
          // emitCss: false,
          // You can pass any of the Svelte compiler oiptions
          // compilerOptions: {

          //     // By default, the client-side compiler is used. You
          //     // can also use the server-side rendering compiler
          //     generate: 'ssr',

          //     // ensure that extra attributes are added to head
          //     // elements for hydration (used with generate: 'ssr')
          //     hydratable: true,

          //     // You can optionally set 'customElement' to 'true' to compile
          //     // your components to custom elements (aka web elements)
          //     customElement: false
          // }
      }),
      json(),
      resolve({
          browser: true,
          // dedupe: ['svelte']
          dedupe: importee =>
            importee === 'svelte' || importee.startsWith('svelte/')
      }),
      commonjs(),

      // In dev mode, call `npm run start` once
      // the bundle has been generated
      !production && serve(),

      // Watch the `public` directory and refresh the
      // browser on changes when not in production
      !production && livereload('src/main/resources/static'),

      // If we're building for production (npm run build
      // instead of npm run dev), minify
      production && terser()
    ],
    watch: {
        clearScreen: false
    }
}

function serve () {
    let started = false

    return {
        writeBundle () {
            if (!started) {
                started = true

                require('child_process').spawn('npm', ['run', 'start', '--', '--dev'], {
                    stdio: ['ignore', 'inherit', 'inherit'],
                    shell: true
                })
            }
        }
    }
}