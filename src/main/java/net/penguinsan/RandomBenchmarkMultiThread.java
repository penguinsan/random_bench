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
@Threads(4)
public class RandomBenchmarkMultiThread {

    private static final int BOUND = 10000;

    private Random secureRandom;
    private Random jdkRandom;
    private ThreadLocal<Random> threadLocalJdkRandom;
    private ThreadLocal<RandomGenerator> threadLocalJdk17Xoroshiro128ppRandom;
    private ThreadLocal<RandomGenerator> threadLocalJdk17L64X128MixRandom;

    @Setup(Level.Trial)
    public void setup() throws NoSuchAlgorithmException {
        secureRandom = SecureRandom.getInstance("NativePRNGNonBlocking"); // Windowsで実行するならWindows-PRNGに変更する
        jdkRandom = new Random();
        threadLocalJdkRandom = new ThreadLocal<Random>() {
            @Override
            protected Random initialValue() {
                return new Random();
            }
        };
        threadLocalJdk17Xoroshiro128ppRandom = new ThreadLocal<>(){
            @Override
            protected RandomGenerator initialValue() {
                return RandomGenerator.of("Xoroshiro128PlusPlus");
            }
        };
        threadLocalJdk17L64X128MixRandom = new ThreadLocal<>() {
            @Override
            protected RandomGenerator initialValue() {
                return RandomGenerator.of("L64X128MixRandom");
            }
        };
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
    public void threadLocalJdkRandom() {
        threadLocalJdkRandom.get().nextInt(BOUND);
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

    @Benchmark
    public void threadLocalJdk17Xoroshiro128ppRandom() {
        threadLocalJdk17Xoroshiro128ppRandom.get().nextInt(BOUND);
    }

    @Benchmark
    public void threadLocalJdk17L64X128MixRandom() {
        threadLocalJdk17L64X128MixRandom.get().nextInt(BOUND);
    }
}
