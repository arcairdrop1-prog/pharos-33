import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class DappSupportAgent {

    public static class TransactionReceipt {
        String txHash;
        boolean success;
        String rawErrorOrRevertReason;
        long gasUsed;
        long gasLimit;
        double userBalance;
        double requiredAmount;

        public TransactionReceipt(String txHash, boolean success, String rawError, long gasUsed, long gasLimit, double userBalance, double requiredAmount) {
            this.txHash = txHash;
            this.success = success;
            this.rawErrorOrRevertReason = rawError;
            this.gasUsed = gasUsed;
            this.gasLimit = gasLimit;
            this.userBalance = userBalance;
            this.requiredAmount = requiredAmount;
        }
    }

    public static class SupportAnalysis {
        String failureCause;
        String plainLanguageExplanation;
        String suggestedFix;

        public SupportAnalysis(String cause, String explanation, String fix) {
            this.failureCause = cause;
            this.plainLanguageExplanation = explanation;
            this.suggestedFix = fix;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("--------------------------------------------------\n");
            sb.append("🛠️ DAPP SUPPORT ANALYSIS REPORT\n");
            sb.append("--------------------------------------------------\n");
            sb.append("Failure Cause: ").append(failureCause).append("\n");
            sb.append("Explanation:   ").append(plainLanguageExplanation).append("\n");
            sb.append("Suggested Fix: ").append(suggestedFix).append("\n");
            sb.append("--------------------------------------------------");
            return sb.toString();
        }
    }

    // Simulated Chain MCP database storing transaction receipts by hash
    private final Map<String, TransactionReceipt> chainMcpDatabase = new HashMap<>();

    public DappSupportAgent() {
        // Populating 3 different failure types for demonstration (Requirement 04)
        chainMcpDatabase.put("0xFAIL_OUT_OF_GAS", new TransactionReceipt(
            "0xFAIL_OUT_OF_GAS", false, "OUT_OF_GAS", 50000L, 50000L, 10.0, 1.0
        ));
        chainMcpDatabase.put("0xFAIL_INSUFFICIENT_BALANCE", new TransactionReceipt(
            "0xFAIL_INSUFFICIENT_BALANCE", false, "EXECUTION_REVERTED", 21000L, 100000L, 0.05, 1.5
        ));
        chainMcpDatabase.put("0xFAIL_REVERT_CUSTOM", new TransactionReceipt(
            "0xFAIL_REVERT_CUSTOM", false, "CustomError: InsufficientAllowance", 35000L, 100000L, 10.0, 1.0
        ));
    }

    // Requirement 01: Accepts a tx hash and fetches the transaction and receipt via Chain MCP[cite: 12]
    public TransactionReceipt fetchReceiptViaChainMCP(String txHash) {
        System.out.println("[Chain MCP] Querying network for transaction receipt: " + txHash + "...");
        return chainMcpDatabase.get(txHash);
    }

    // Requirement 02 & 03: Identifies failure cause and explains it without jargon with a suggested fix[cite: 12]
    public SupportAnalysis analyzeTransaction(String txHash) {
        TransactionReceipt receipt = fetchReceiptViaChainMCP(txHash);

        if (receipt == null) {
            return new SupportAnalysis("NOT_FOUND", "Transaction hash not found on the Pharos network.", "Verify the transaction hash and try again.");
        }

        if (receipt.success) {
            return new SupportAnalysis("SUCCESS", "This transaction executed successfully with no errors.", "No action needed.");
        }

        // Requirement 02: Identify failure cause (out of gas, insufficient balance, allowance/revert reason)[cite: 12]
        if (receipt.rawErrorOrRevertReason.contains("OUT_OF_GAS") || receipt.gasUsed >= receipt.gasLimit) {
            return new SupportAnalysis(
                "Out of Gas",
                "Your transaction ran out of computational steps (gas) before it could finish executing.",
                "Try submitting the transaction again but increase your gas limit by at least 20%."
            );
        }

        if (receipt.userBalance < receipt.requiredAmount) {
            return new SupportAnalysis(
                "Insufficient Balance",
                "You do not have enough tokens or native currency in your wallet to cover the cost of this transaction.",
                "Top up your wallet balance with more tokens or testnet funds and try again."
            );
        }

        if (receipt.rawErrorOrRevertReason.contains("Allowance") || receipt.rawErrorOrRevertReason.contains("Revert")) {
            return new SupportAnalysis(
                "Insufficient Allowance / Smart Contract Revert",
                "The smart contract rejected the transaction because you haven't approved it to spend your tokens yet, or a condition in the contract failed.",
                "Approve the contract to spend your tokens first before attempting the swap or deposit."
            );
        }

        return new SupportAnalysis(
            "Unknown Revert",
            "The smart contract reverted the transaction due to an unspecified internal condition.",
            "Check the contract requirements or consult community support."
        );
    }

    public static void main(String[] args) {
        DappSupportAgent agent = new DappSupportAgent();

        System.out.println("=== DAPP SUPPORT AGENT SIMULATION ===");

        // Requirement 04: Demonstrated on at least 3 different failure types[cite: 12]
        String[] testHashes = {
            "0xFAIL_OUT_OF_GAS",
            "0xFAIL_INSUFFICIENT_BALANCE",
            "0xFAIL_REVERT_CUSTOM"
        };

        for (int i = 0; i < testHashes.length; i++) {
            System.out.println("\n[Test Case #" + (i + 1) + "]: Analyzing hash " + testHashes[i]);
            SupportAnalysis analysis = agent.analyzeTransaction(testHashes[i]);
            System.out.println(analysis);
        }
    }
}
