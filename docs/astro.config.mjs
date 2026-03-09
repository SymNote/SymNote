// @ts-check
import { defineConfig } from 'astro/config';
import starlight from '@astrojs/starlight';
import starlightThemeNext from 'starlight-theme-next'

// https://astro.build/config
export default defineConfig({
    integrations: [
        starlight({
            plugins: [starlightThemeNext()],
            title: 'SymNote Docs',
			social: [{ icon: 'github', label: 'GitHub', href: 'https://github.com/trojancoding/SymNote' }],
            sidebar: [
				{ label: 'Introduction', link: '/' },
                {
                    label: 'Core Logic',
                    items: [
                        { label: 'Language Principles', link: '/logic/principles' },
                        { label: 'Rule System', link: '/logic/rules' },
                    ],
                },
                {
                    label: 'Technical Reference',
                    autogenerate: { directory: 'reference' },
                },
            ],
        }),
    ],
});