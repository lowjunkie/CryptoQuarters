package com.lj.cryptoquarters.domain.use_case.get_coin

import com.lj.cryptoquarters.common.Resource
import com.lj.cryptoquarters.data.remote.dto.CoinDetailDto
import com.lj.cryptoquarters.data.remote.dto.toCoin
import com.lj.cryptoquarters.data.remote.dto.toCoinDetail
import com.lj.cryptoquarters.data.repository.Coin
import com.lj.cryptoquarters.data.repository.CoinDetail
import com.lj.cryptoquarters.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetCoinUseCase @Inject constructor(
    private val repository: CoinRepository
) {
    operator fun invoke(coinId: String): Flow<Resource<CoinDetail>> = flow{
        try{
            emit(Resource.Loading<CoinDetail>())
            val coin = repository.getCoinById(coinId).toCoinDetail()
            emit(Resource.Success<CoinDetail>(coin))
        }catch (e: HttpException){
            emit(Resource.Error<CoinDetail>(e.localizedMessage ?: "An unexpected error occurred."))
        }catch (e: IOException){
            emit(Resource.Error<CoinDetail>("Couldn't reach server. Check your internet connection."))
        }
    }
}