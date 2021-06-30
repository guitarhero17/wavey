<script>
  import ContentLoader from 'svelte-content-loader'
  import { fade } from 'svelte/transition'

  export let animateCL = true
</script>

<style>

</style>

<article transition:fade>
  <section class="intro-section bg-black w-full text-white px-5 pt-20 pb-10">
    <div class="intro-wrapper relative">
      <div class="flex items-center">
        <div class="intro-photo w-1/2 s:w-full h-auto">
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
      <div class="intro-name mt-8">
        <div class="text-2xl">This is</div>
        <ContentLoader width="300px" height="60px" primaryColor="#111827" animate= { animateCL }>
          <rect x="0" y="0" rx="4" ry="4" width="100%" height="60px"/>
        </ContentLoader>
      </div>
    </div>
    <div class="profile-info mt-20 flex">
      <div class="info-instruments-container w-1/2 border-r border-waveyYellow">
        <div class="info-instruments-wrapper px-5">
          <div class="info-instrumentPrimary">
            <div class="text-3xl mb-4">Primarily plays</div>
            <div class="flex items-center">
              <div class="info-instrument-icon mr-2 rounded-full w-12 h-12">
                <ContentLoader width="48px" height="48px" primaryColor="#111827" animate= { animateCL }>
                  <circle cx="24px" cy="24px" r="24px"/>
                </ContentLoader>
              </div>
              <ContentLoader width="100px" height="20px" primaryColor="#111827" animate= { animateCL }>
                <rect x="0" y="0" rx="4" ry="4" width="100%" height="20px"/>
              </ContentLoader>
            </div>
          </div>
        </div>
      </div>
      <div class="info-lookingFor-container w-1/2">
        <div class="info-lookingFor-wrapper px-5">
          <div class="text-3xl mb-4">Currently is on the lookout for</div>
        </div>
      </div>
    </div>
  </section>
  <section class="waves-section bg-gray-900 w-full text-white px-5 py-10 border-t border-waveyYellow">
    <div class="waves-title text-4xl mt-2">
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