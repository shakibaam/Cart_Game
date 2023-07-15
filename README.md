# Card Game

This project is a card game where you need to arrange a set of cards with different colors and numbers in a specific order.

## Rules

1. The game board consists of `k` sections, and an infinite number of cards can be placed in each section. All cards are visible.
2. Each game has `m` colors (where `k <= m`), and each color has `n` cards with numbers from 1 to `n`.
3. The objective is to have all cards in each section of the same color and arrange the numbers in descending order (top to bottom or left to right).
4. During each turn, you can pick the lowest card from any section and place it on another section. The card can only be placed on a card with a higher number. Empty rows can also be used.

## Solution Approach

We solve this problem by BFS, IDS , A* alogorithms.
