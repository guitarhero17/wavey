<script>
  import ContentLoader from 'svelte-content-loader'

  export let animateCL = true
</script>

<style>

</style>

<article>
  <section class="w-full px-5 pt-20 pb-10">
    <div class="relative">
      <div class="flex items-center">
        <div class="w-1/2 s:w-full h-auto">
          <ContentLoader width="100%" height="280px" primaryColor="#111827" animate= { animateCL }>
            <rect x="0" y="0" rx="11" ry="11" width="100%" height="100%"/>
          </ContentLoader>
        </div>
        <div class="s:hidden chat-wrapper w-1/2 flex justify-center">
          <ContentLoader width="160px" height="160px" primaryColor="#111827" animate= { animateCL }>
            <circle cx="80px" cy="80px" r="80px" />
          </ContentLoader>
        </div>
      </div>
      <div class="mt-8">
        <div class="text-2xl">This is</div>
        <ContentLoader width="300px" height="60px" primaryColor="#111827" animate= { animateCL }>
          <rect x="0" y="0" rx="4" ry="4" width="100%" height="60px"/>
        </ContentLoader>
      </div>
    </div>
    <div class="mt-20 flex">
      <div class="w-1/2 px-5 border-r border-waveyYellow">
        <div class="text-3xl mb-4">Primarily plays</div>
        <div class="flex items-center">
          <div class="mr-2 rounded-full w-12 h-12">
            <ContentLoader width="48px" height="48px" primaryColor="#111827" animate= { animateCL }>
              <circle cx="24px" cy="24px" r="24px"/>
            </ContentLoader>
          </div>
          <ContentLoader width="100px" height="20px" primaryColor="#111827" animate= { animateCL }>
            <rect x="0" y="0" rx="4" ry="4" width="100%" height="20px"/>
          </ContentLoader>
        </div>
      </div>
      <div class="w-1/2 px-5">
        <div class="text-3xl mb-4">Currently is on the lookout for</div>
      </div>
    </div>
  </section>
  <section class="bg-gray-900 w-full px-5 py-10 border-t border-waveyYellow">
    <div class="text-4xl mt-2">
      Hear <span class="inline-content-loader">
        <ContentLoader width="100px" height="20px" primaryColor="#111827" animate= { animateCL }>
          <rect x="0" y="0" rx="4" ry="4" width="100%" height="100%"/>
        </ContentLoader>
      </span>'s waves
    </div>
    <div class="mt-6">
      <ContentLoader width="80%" height="200px" primaryColor="#111827" animate= { animateCL }>
        <rect x="0" y="0" rx="4" ry="4" width="100%" height="100%"/>
      </ContentLoader>
    </div>
  </section>
</article>