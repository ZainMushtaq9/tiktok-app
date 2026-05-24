// This file should ONLY be imported in server-side contexts / API routes
export const serverConfig = {
  scrapingServiceUrl: process.env.SCRAPING_SERVICE_URL || '',
  scrapingServiceKey: process.env.SCRAPING_SERVICE_KEY || '',
};

export function validateServerEnv() {
  const missing = [];
  if (!serverConfig.scrapingServiceUrl) missing.push('SCRAPING_SERVICE_URL');
  if (!serverConfig.scrapingServiceKey) missing.push('SCRAPING_SERVICE_KEY');

  if (missing.length > 0) {
    console.warn(`⚠️ CRITICAL: Missing server environment variables: ${missing.join(', ')}. Backend operations will fail.`);
  }
}
