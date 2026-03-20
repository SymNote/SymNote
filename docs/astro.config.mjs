// @ts-check
import { defineConfig } from 'astro/config';
import starlight from '@astrojs/starlight';
import starlightThemeNext from 'starlight-theme-next'

// https://astro.build/config
export default defineConfig({
    site: 'https://trojancoding.github.io',
    base: '/SymNote',
    integrations: [
        starlight({
            plugins: [starlightThemeNext()],
            title: 'SymNote Docs',
            customCss: ['./src/styles/custom.css'],
			social: [{ icon: 'github', label: 'GitHub', href: 'https://github.com/trojancoding/SymNote' }],
            sidebar: [
            { label: 'Introduction', link: '/' },

            {
                label: 'Core Concepts',
                autogenerate: { directory: 'logic' },
            },

            {
                label: 'Architecture',
                items: [
                {
                    label: 'Level 1 (Timeline)',
                    autogenerate: { directory: 'architecture/level-1' },
                },
                {
                    label: 'Level 2 (Track)',
                    autogenerate: { directory: 'architecture/level-2/track' },
                },
                {
                    label: 'Level 3 (Grid)',
                    autogenerate: { directory: 'architecture/level-3/grid' },
                },
                ],
            },

            {
                label: 'Technical Reference',
                autogenerate: { directory: 'reference' },
            },
            ]
        }),
    ],
});