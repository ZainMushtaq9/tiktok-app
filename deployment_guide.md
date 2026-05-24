# Deployment Guide (Next.js on Vercel)

This project has been optimized for global edge delivery focusing on speed and automated SEO routing.

## Step 1: Push to GitHub
Upload the web/ portion of this repository to GitHub or your preferred Git provider.

## Step 2: Vercel Setup
1. Go to Vercel (vercel.com/new).
2. Import the Git repository.
3. Vercel will automatically detect the **Next.js** framework.
4. Set the Root Directory if necessary.

## Step 3: Environment Variables
Copy the variables found in `.env.example` into the Vercel dashboard **Settings -> Environment Variables**.
- Add your Firebase keys.
- Add your Google AdSense Publisher ID (`NEXT_PUBLIC_ADSENSE_CLIENT_ID`).

## Step 4: CDN and Edge Optimization
The `vercel.json` file is pre-configured with caching headers for SSR responses and immutable static assets. Next.js naturally provides Image Optimization; make sure all `next/image` tags specify `src` appropriately.

## Programmatic SEO Strategy
- Use Next.js `getStaticPaths` or dynamic API routes for `/tools/[platform]`.
- Submit the `/sitemap.xml` generated route to Google Search Console to index endpoints like `tiktok-profile-downloader`.
