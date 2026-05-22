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
                    label: 'User Documentation',
                    autogenerate: { directory: 'user' },
                },
                {
                    label: 'Technical Report',
                    autogenerate: { directory: 'technical' },
                }
            ]
        }),
    ],
});