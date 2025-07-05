import { z } from 'zod';

export const SearchSchema = z.object({
  title: z.string(),
  location: z.string(),
});

export type SearchType = z.infer<typeof SearchSchema>;
