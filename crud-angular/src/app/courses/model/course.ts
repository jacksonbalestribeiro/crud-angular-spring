import { Lesson } from './Lesson';

export interface Course {
  _id: string;
  name: string;
  category: string;
  lessons?: Lesson[];
}
