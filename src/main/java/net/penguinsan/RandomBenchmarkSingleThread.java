package net.penguinsan;

import org.apache.commons.rng.simple.RandomSource;
import org.apache.commons.rng.simple.ThreadLocalRandomSource;
import org.openjdk.jmh.annotations.*;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.random.RandomGenerator;

@State(Scope.Benchmark)
@Fork(1)
public class RandomBenchmarkSingleThread {

    private static final int BOUND = 10000;

    private Random secureRandom;
    private Random jdkRandom;
    private RandomGenerator jdk17Xoroshiro128ppRandom;
    private RandomGenerator jdk17L64X128MixRandom;

    @Setup(Level.Trial)
    public void setup() throws NoSuchAlgorithmException {
        secureRandom = SecureRandom.getInstance("NativePRNGNonBlocking"); // Windowsで実行するならWindows-PRNGに変更する
        jdkRandom = new Random();
        jdk17Xoroshiro128ppRandom = RandomGenerator.of("Xoroshiro128PlusPlus");
        jdk17L64X128MixRandom = RandomGenerator.of("L64X128MixRandom");
    }

    @Benchmark
    public void secureRandom() {
        secureRandom.nextInt(BOUND);
    }

    @Benchmark
    public void jdkRandom() {
        jdkRandom.nextInt(BOUND);
    }

    @Benchmark
    public void jdk17Xoroshiro128ppRandom() {
        jdk17Xoroshiro128ppRandom.nextInt(BOUND);
    }

    @Benchmark
    public void jdk17L64X128MixRandom() {
        jdk17L64X128MixRandom.nextInt(BOUND);
    }

    @Benchmark
    public void threadLocalRandom() {
        ThreadLocalRandom.current().nextInt(BOUND);
    }

    @Benchmark
    public void threadLocalCommonsRngMT_64Random() {
        ThreadLocalRandomSource.current(RandomSource.MT_64).nextInt(BOUND);
    }

    @Benchmark
    public void threadLocalCommonsRngXO_RO_SHI_RO_128_PPRandom() {
        ThreadLocalRandomSource.current(RandomSource.XO_RO_SHI_RO_128_PP).nextInt(BOUND);
    }

    @Benchmark
    public void threadLocalCommonsRngPCG_XSH_RR_32Random() {
        ThreadLocalRandomSource.current(RandomSource.PCG_XSH_RR_32).nextInt(BOUND);
    }

    @Benchmark
    public void threadLocalCommonsRngSPLIT_MIX_64Random() {
        ThreadLocalRandomSource.current(RandomSource.SPLIT_MIX_64).nextInt(BOUND);
    }

    @Benchmark
    public void threadLocalCommonsRngWELL_1024_ARandom() {
        ThreadLocalRandomSource.current(RandomSource.WELL_1024_A).nextInt(BOUND);
    }
}
