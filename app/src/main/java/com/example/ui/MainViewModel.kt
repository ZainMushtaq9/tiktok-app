package com.example.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ai.GeminiService
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

data class QueueItem(
    val id: Int,
    val url: String,
    val filename: String,
    val progress: Float,
    val status: String
)

data class SeoRoute(
    val route: String,
    val title: String,
    val h1: String,
    val description: String,
    val keywords: String,
    val schemaMarkup: String
)

data class CreatorArticle(
    val title: String,
    val category: String,
    val readTime: String,
    val intro: String,
    val content: String
)

class MainViewModel : ViewModel() {

    // --- Downloader State ---
    private val _queue = MutableStateFlow<List<QueueItem>>(
        listOf(
            QueueItem(1, "https://www.tiktok.com/@creator/video/987162", "profile_tiktok_video_1716499.mp4", 1.0f, "Completed"),
            QueueItem(2, "https://www.instagram.com/p/C9823haM", "instagram_reels_video_1716503.mp4", 0.0f, "In Queue")
        )
    )
    val queue = _queue.asStateFlow()

    private val _isDownloading = MutableStateFlow(false)
    val isDownloading = _isDownloading.asStateFlow()

    private val _downloadProgress = MutableStateFlow(0f)
    val downloadProgress = _downloadProgress.asStateFlow()

    // --- AI Generator State ---
    private val _isGenerating = MutableStateFlow(false)
    val isGenerating = _isGenerating.asStateFlow()

    private val _generatedResult = MutableStateFlow("")
    val generatedResult = _generatedResult.asStateFlow()

    // --- Cookie Consent ---
    private val _cookieConsentAccepted = MutableStateFlow(false)
    val cookieConsentAccepted = _cookieConsentAccepted.asStateFlow()

    // --- Programmatic SEO Routes ---
    val seoRoutes = listOf(
        SeoRoute(
            route = "/download-tiktok-no-watermark",
            title = "Download TikTok Video Without Watermark (HD) - ReelStack",
            h1 = "Free HD TikTok Video Downloader",
            description = "Download TikTok videos without watermark in premium high definition. Fast, secure, and fully responsive processing.",
            keywords = "tiktok, downloader, no watermark, save tik, hd tiktok",
            schemaMarkup = """{
  "@context": "https://schema.org",
  "@type": "SoftwareApplication",
  "name": "ReelStack TikTok Utility",
  "operatingSystem": "Android, iOS, Web",
  "applicationCategory": "MultimediaApplication",
  "offers": { "@type": "Offer", "price": "0.00", "priceCurrency": "USD" }
}"""
        ),
        SeoRoute(
            route = "/tiktok-mp3-downloader",
            title = "TikTok Audio & MP3 Downloader - ReelStack",
            h1 = "Extract High-Quality TikTok MP3s",
            description = "Instantly extract and download audio files from any TikTok video. Highly optimized for creator sound tracking.",
            keywords = "tiktok to mp3, extract tik audio, tik sound saver",
            schemaMarkup = """{
  "@context": "https://schema.org",
  "@type": "WebApplication",
  "name": "ReelStack Sound Extractor",
  "browserRequirements": "Requires HTML5"
}"""
        ),
        SeoRoute(
            route = "/tiktok-profile-downloader",
            title = "TikTok Profile & Batch Downloader - ReelStack",
            h1 = "Download Entire Creator Profiles",
            description = "Queue and download active video streams directly from a public profile url in one simple batch process.",
            keywords = "profile downloader, tiktok batch save, creator archive",
            schemaMarkup = """{
  "@context": "https://schema.org",
  "@type": "SoftwareApplication",
  "name": "ReelStack Profile Extractor"
}"""
        ),
        SeoRoute(
            route = "/instagram-reels-downloader",
            title = "Instagram Reels & Video Saver (1080p) - ReelStack",
            h1 = "Instagram Reels Downloader",
            description = "Download Instagram stories, posts, and IGTV media directly. Fully compliant with public content parameters.",
            keywords = "instagram downloader, download reels, save ig videos",
            schemaMarkup = """{
  "@context": "https://schema.org",
  "@type": "SoftwareApplication",
  "name": "ReelStack Instagram Downloader"
}"""
        )
    )

    // --- Creator Tips Blog Articles ---
    val creatorArticles = listOf(
        CreatorArticle(
            title = "Maximizing AdSense RPM for Creator Tools",
            category = "Monetization",
            readTime = "5 min read",
            intro = "Creator tools and media utilities typically enjoy some of the highest RPM rates in the industry. Discover how to leverage strategic layouts & header bid parameters for 2x revenue growth.",
            content = "To capture high RPM on tool utility layouts, position native units directly below input zones. Place responsive sticky banner widgets at visual safe areas to guarantee impressions. Keep layouts fast to satisfy Core Web Vitals, and populate creator content structures to prompt higher bidder competition."
        ),
        CreatorArticle(
            title = "Algorithmic Triggering for Short-Form Video",
            category = "Virality",
            readTime = "4 min read",
            intro = "How watch time thresholds, metric loops, and dynamic visual hooks convince modern short-form recommendation parsers to distribute your reels to millions.",
            content = "Short-form platforms prioritize dynamic user loops. Ensure your first 1.5 seconds have an aggressive focal hook. End with an unresolved cliffhanger or a loop trigger that seamlessly replays the video. Encourage active, controversial opinion polls in description captions to scale engagement comments."
        ),
        CreatorArticle(
            title = "programmatic SEO (pSEO) Scaling Guide",
            category = "Traffic Optimization",
            readTime = "6 min read",
            intro = "An in-depth strategy to generate hundreds of high-ranking landing queries safely by targeting long-tail modifiers rather than saturated main phrases.",
            content = "Start by locating long-tail queries like 'download reels without watermark iphone safari'. Map these with dynamic, structured Schema formats to feed search engine robots. Create high-frequency sitemap configurations and update them programmatically. Pair target layouts with relevant expert columns to establish dynamic topical authority."
        )
    )

    // --- Viral Hashtags ---
    val trendingHashtags = listOf(
        "#foryoupage" to "98.4B views",
        "#tiktokgrowth" to "12.3M views",
        "#viralreels" to "87.1M views",
        "#creatorgems" to "4.2M views",
        "#boostrpm" to "1.5M views",
        "#techtrends" to "14.8M views"
    )

    // --- Trending Sounds ---
    val trendingSounds = listOf(
        "Cosmic Slate Beat (Speed Up)" to "345K posts",
        "Summer Horizons Acoustic" to "890K posts",
        "Ethereal Whispers (Ambient LoFi)" to "1.2M posts",
        "Glitch Synth Wave" to "120K posts"
    )

    // --- Sitemap and Robots XML Generators ---
    fun generateSitemap(): String {
        val date = "2026-05-23"
        val sb = StringBuilder()
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n")
        sb.append("<urlset xmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\">\n")
        sb.append("  <url>\n    <loc>https://reelstack.com/</loc>\n    <lastmod>$date</lastmod>\n    <priority>1.0</priority>\n  </url>\n")
        seoRoutes.forEach {
            sb.append("  <url>\n    <loc>https://reelstack.com${it.route}</loc>\n    <lastmod>$date</lastmod>\n    <priority>0.8</priority>\n  </url>\n")
        }
        sb.append("</urlset>")
        return sb.toString()
    }

    fun generateRobots(): String {
        return """
            User-agent: *
            Allow: /
            Disallow: /api/
            Disallow: /admin/
            
            Sitemap: https://reelstack.com/sitemap.xml
        """.trimIndent()
    }

    // --- Actions ---

    fun setCookieConsent(accepted: Boolean) {
        _cookieConsentAccepted.value = accepted
    }

    fun addDownloadItem(url: String, filename: String) {
        val trimmed = url.trim()
        if (trimmed.isEmpty()) return
        viewModelScope.launch {
            _isDownloading.value = true
            _downloadProgress.value = 0f

            // Add the item to list as Pending
            val id = (_queue.value.maxOfOrNull { it.id } ?: 0) + 1
            val cleanFilename = if (filename.isBlank()) "video_${id}.mp4" else filename
            val newItem = QueueItem(id, trimmed, cleanFilename, 0f, "Downloading")
            _queue.value = _queue.value + newItem

            // Simulate beautiful visual download progression steps
            for (i in 1..10) {
                delay(300)
                _downloadProgress.value = i / 10f
                _queue.value = _queue.value.map { item ->
                    if (item.id == id) item.copy(progress = i / 10f) else item
                }
            }

            // Mark as Completed
            _queue.value = _queue.value.map { item ->
                if (item.id == id) item.copy(progress = 1.0f, status = "Completed") else item
            }
            _isDownloading.value = false
            _downloadProgress.value = 0f
        }
    }

    fun removeQueueItem(id: Int) {
        _queue.value = _queue.value.filter { it.id != id }
    }

    /**
     * AI generation calls handled seamlessly using model-backed system orfallback
     */
    fun startAIGenerator(mode: String, topic: String) {
        if (topic.trim().isEmpty()) return
        viewModelScope.launch {
            _isGenerating.value = true
            _generatedResult.value = ""

            val prompt = when (mode) {
                "hashtags" -> "Generate hashtags for topic: $topic"
                "captions" -> "Generate captions for topic: $topic"
                else -> "Generate bio for topic/creator: $topic"
            }

            val systemPrompt = when (mode) {
                "hashtags" -> "You are an SEO hashtag generator expert. Output 15 powerful, high-RPM viral hashtags based on the topic. Provide tags separated only by spaces."
                "captions" -> "You are a professional creator copywriter. Generate 3 engaging hooks and description text options with clear line breaks. Avoid generic slop."
                else -> "You are a personal branding expert. Generate 3 magnetic social media bio variations matching the persona. Use crisp lines, creative indicators, & clear calls-to-action."
            }

            val response = GeminiService.generateTask(prompt, systemPrompt)
            _generatedResult.value = response
            _isGenerating.value = false
        }
    }
}
