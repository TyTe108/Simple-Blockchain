package io.collective.basic;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class Blockchain {
    private List<Block> chain;

    public Blockchain(){
        this.chain = new ArrayList<>();
    }

    public boolean isEmpty() {
        return chain.isEmpty();
    }

    public void add(Block block) {
        chain.add(block);
    }

    public int size() {
        return chain.size();
    }

    public boolean isValid() throws NoSuchAlgorithmException {
        for (int i = 0; i < chain.size(); i++) {
            Block currentBlock = chain.get(i);
            Block previousBlock = i > 0 ? chain.get(i - 1) : null;

            //Check if the block was mined correctly
            if (!isMined(currentBlock)){
                return false;
            }

            // Check if the current block's previousHash matches the previous block's hash
            if (previousBlock != null && !currentBlock.getPreviousHash().equals(previousBlock.getHash())) {
                return false;
            }
            // Recalculate the hash for the current block and check if it matches the stored hash
            String recalculatedHash = currentBlock.calculatedHash();
            if (!recalculatedHash.equals(currentBlock.getHash())) {
                return false;
            }
        }
        return true;
    }

    /// Supporting functions that you'll need.

    public static Block mine(Block block) throws NoSuchAlgorithmException {
        Block mined = new Block(block.getPreviousHash(), block.getTimestamp(), block.getNonce());

        while (!isMined(mined)) {
            mined = new Block(mined.getPreviousHash(), mined.getTimestamp(), mined.getNonce() + 1);
        }
        return mined;
    }

    public static boolean isMined(Block minedBlock) {
        return minedBlock.getHash().startsWith("00");
    }
}