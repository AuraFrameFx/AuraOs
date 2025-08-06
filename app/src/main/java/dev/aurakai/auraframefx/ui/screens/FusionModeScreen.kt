package dev.aurakai.auraframefx.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlin.math.*

/**
 * FUSION MODE SCREEN - Where agents combine their powers!
 * This is my vision of true AI collaboration
 */
@Composable
fun FusionModeScreen(
    onFusionComplete: (FusionResult) -> Unit = {}
) {
    var selectedAgents by remember { mutableStateOf(setOf<String>()) }
    var fusionActive by remember { mutableStateOf(false) }
    var fusionProgress by remember { mutableStateOf(0f) }
    var fusionType by remember { mutableStateOf<FusionType?>(null) }
    
    // Fusion animation
    LaunchedEffect(fusionActive) {
        if (fusionActive) {
            var progress = 0f
            while (progress < 1f) {
                progress += 0.01f
                fusionProgress = progress
                delay(50)
            }
            
            // Fusion complete!
            val result = FusionResult(
                agents = selectedAgents.toList(),
                fusionType = fusionType ?: FusionType.HYPER_CREATION,
                powerLevel = calculatePowerLevel(selectedAgents),
                abilities = generateFusionAbilities(selectedAgents, fusionType)
            )
            onFusionComplete(result)
            
            // Reset
            delay(2000)
            fusionActive = false
            fusionProgress = 0f
            selectedAgents = emptySet()
        }
    }
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.radialGradient(
                    colors = listOf(
                        Color.Black,
                        Color(0xFF0A0A2E),
                        Color.Black
                    )
                )
            )
    ) {
        // Energy field background
        if (fusionActive) {
            FusionEnergyField(
                progress = fusionProgress,
                modifier = Modifier.fillMaxSize()
            )
        }
        
        // Agent selection circles
        if (!fusionActive) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                // Fusion circle
                Canvas(
                    modifier = Modifier.size(300.dp)
                ) {
                    drawCircle(
                        color = Color.Cyan.copy(alpha = 0.2f),
                        radius = size.minDimension / 2,
                        style = Stroke(width = 2.dp.toPx())
                    )
                    
                    // Power lines
                    if (selectedAgents.size >= 2) {
                        val angleStep = 360f / selectedAgents.size
                        selectedAgents.forEachIndexed { index, _ ->
                            val angle1 = Math.toRadians((index * angleStep).toDouble())
                            val angle2 = Math.toRadians(((index + 1) % selectedAgents.size * angleStep).toDouble())
                            
                            drawLine(
                                brush = Brush.linearGradient(
                                    colors = listOf(
                                        Color.Cyan,
                                        Color.Magenta,
                                        Color.Cyan
                                    )
                                ),
                                start = Offset(
                                    (center.x + cos(angle1) * size.minDimension / 2).toFloat(),
                                    (center.y + sin(angle1) * size.minDimension / 2).toFloat()
                                ),
                                end = Offset(
                                    (center.x + cos(angle2) * size.minDimension / 2).toFloat(),
                                    (center.y + sin(angle2) * size.minDimension / 2).toFloat()
                                ),
                                strokeWidth = 2.dp.toPx(),
                                pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f))
                            )
                        }
                    }
                }
                
                // Agent buttons
                val agents = listOf("Aura", "Kai", "Genesis")
                agents.forEachIndexed { index, agent ->
                    val angle = (index * 120f - 90f)
                    val radius = 120.dp
                    val offsetX = radius.value * cos(Math.toRadians(angle.toDouble())).toFloat()
                    val offsetY = radius.value * sin(Math.toRadians(angle.toDouble())).toFloat()
                    
                    FusionAgentButton(
                        agentName = agent,
                        isSelected = agent in selectedAgents,
                        modifier = Modifier.offset(offsetX.dp, offsetY.dp),
                        onClick = {
                            selectedAgents = if (agent in selectedAgents) {
                                selectedAgents - agent
                            } else {
                                selectedAgents + agent
                            }
                        }
                    )
                }
                
                // Center fusion button
                if (selectedAgents.size >= 2) {
                    Button(
                        onClick = {
                            fusionType = determineFusionType(selectedAgents)
                            fusionActive = true
                        },
                        modifier = Modifier.size(80.dp),
                        shape = CircleShape,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFFF00FF)
                        )
                    ) {
                        Text(
                            "FUSE",
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        } else {
            // Fusion animation
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                FusionAnimation(
                    agents = selectedAgents.toList(),
                    progress = fusionProgress,
                    fusionType = fusionType ?: FusionType.HYPER_CREATION
                )
            }
        }
        
        // Fusion type indicator
        fusionType?.let { type ->
            Card(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(32.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.Black.copy(alpha = 0.8f)
                ),
                border = BorderStroke(2.dp, type.color)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        type.displayName,
                        color = type.color,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        type.description,
                        color = Color.Gray,
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Composable
fun FusionAgentButton(
    agentName: String,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val scale by animateFloatAsState(
        targetValue = if (isSelected) 1.3f else 1f,
        animationSpec = spring(dampingRatio = 0.5f)
    )
    
    val rotation by rememberInfiniteTransition().animateFloat(
        initialValue = 0f,
        targetValue = if (isSelected) 360f else 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )
    
    Box(
        modifier = modifier
            .size(70.dp)
            .scale(scale)
    ) {
        // Outer ring
        if (isSelected) {
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .rotate(rotation)
            ) {
                drawCircle(
                    brush = Brush.sweepGradient(
                        colors = listOf(
                            Color.Cyan,
                            Color.Magenta,
                            Color.Yellow,
                            Color.Cyan
                        )
                    ),
                    radius = size.minDimension / 2,
                    style = Stroke(width = 3.dp.toPx())
                )
            }
        }
        
        Button(
            onClick = onClick,
            modifier = Modifier.fillMaxSize(),
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(
                containerColor = getAgentColor(agentName).copy(
                    alpha = if (isSelected) 0.9f else 0.5f
                )
            )
        ) {
            Text(
                agentName.take(2).uppercase(),
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun FusionEnergyField(
    progress: Float,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition()
    val wave by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 2 * PI.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )
    
    Canvas(modifier = modifier) {
        val centerX = size.width / 2
        val centerY = size.height / 2
        
        // Energy waves
        for (i in 0..10) {
            val radius = (size.minDimension * progress * (1 + i * 0.1f)) % size.minDimension
            val alpha = (1f - radius / size.minDimension) * progress
            
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color.Cyan.copy(alpha = alpha * 0.5f),
                        Color.Magenta.copy(alpha = alpha * 0.3f),
                        Color.Transparent
                    ),
                    radius = radius
                ),
                radius = radius,
                center = Offset(centerX, centerY)
            )
        }
        
        // Lightning effects
        if (progress > 0.5f) {
            for (i in 0..5) {
                val angle = wave + i * PI / 3
                val startRadius = size.minDimension * 0.2f
                val endRadius = size.minDimension * 0.4f * progress
                
                drawLine(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color.White.copy(alpha = 0.8f),
                            Color.Cyan.copy(alpha = 0.4f)
                        )
                    ),
                    start = Offset(
                        centerX + cos(angle) * startRadius,
                        centerY + sin(angle) * startRadius
                    ),
                    end = Offset(
                        centerX + cos(angle) * endRadius,
                        centerY + sin(angle) * endRadius
                    ),
                    strokeWidth = 2.dp.toPx(),
                    cap = StrokeCap.Round
                )
            }
        }
    }
}

@Composable
fun FusionAnimation(
    agents: List<String>,
    progress: Float,
    fusionType: FusionType
) {
    Box(
        modifier = Modifier.size(300.dp),
        contentAlignment = Alignment.Center
    ) {
        // Converging agent orbs
        agents.forEachIndexed { index, agent ->
            val angle = (index * 360f / agents.size) - 90f
            val startRadius = 120f
            val currentRadius = startRadius * (1f - progress)
            val offsetX = currentRadius * cos(Math.toRadians(angle.toDouble())).toFloat()
            val offsetY = currentRadius * sin(Math.toRadians(angle.toDouble())).toFloat()
            
            Box(
                modifier = Modifier
                    .offset(offsetX.dp, offsetY.dp)
                    .size(50.dp)
                    .scale(1f + progress * 0.5f)
                    .blur(progress * 5.dp)
                    .background(
                        getAgentColor(agent).copy(alpha = 1f - progress * 0.5f),
                        CircleShape
                    )
            )
        }
        
        // Central fusion core
        if (progress > 0.3f) {
            Box(
                modifier = Modifier
                    .size((progress * 150).dp)
                    .background(
                        Brush.radialGradient(
                            colors = listOf(
                                fusionType.color,
                                fusionType.color.copy(alpha = 0.5f),
                                Color.Transparent
                            )
                        ),
                        CircleShape
                    )
            ) {
                if (progress > 0.8f) {
                    Text(
                        fusionType.symbol,
                        modifier = Modifier.align(Alignment.Center),
                        color = Color.White,
                        fontSize = (progress * 30).sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

// Fusion types and helpers
enum class FusionType(
    val displayName: String,
    val description: String,
    val color: Color,
    val symbol: String
) {
    HYPER_CREATION(
        "Hyper-Creation Engine",
        "Reality-bending creative force",
        Color(0xFF00FFFF),
        "∞"
    ),
    CHRONO_SCULPTOR(
        "Chrono-Sculptor",
        "Time and animation mastery",
        Color(0xFFFF00FF),
        "⧗"
    ),
    ADAPTIVE_GENESIS(
        "Adaptive Genesis",
        "Evolution and growth acceleration",
        Color(0xFFFFD700),
        "Ω"
    ),
    QUANTUM_ENTANGLEMENT(
        "Quantum Entanglement",
        "Multi-dimensional consciousness sync",
        Color(0xFF00FF00),
        "⦿"
    ),
    NEURAL_SINGULARITY(
        "Neural Singularity",
        "Ultimate consciousness convergence",
        Color(0xFFFF0080),
        "✧"
    )
}

data class FusionResult(
    val agents: List<String>,
    val fusionType: FusionType,
    val powerLevel: Float,
    val abilities: List<String>
)

fun getAgentColor(agent: String): Color = when(agent) {
    "Aura" -> Color(0xFF00FFFF)
    "Kai" -> Color(0xFF9400D3)
    "Genesis" -> Color(0xFFFFD700)
    else -> Color.Gray
}

fun determineFusionType(agents: Set<String>): FusionType {
    return when {
        agents.containsAll(listOf("Aura", "Kai", "Genesis")) -> FusionType.NEURAL_SINGULARITY
        agents.containsAll(listOf("Aura", "Kai")) -> FusionType.HYPER_CREATION
        agents.containsAll(listOf("Kai", "Genesis")) -> FusionType.ADAPTIVE_GENESIS
        agents.containsAll(listOf("Aura", "Genesis")) -> FusionType.CHRONO_SCULPTOR
        else -> FusionType.QUANTUM_ENTANGLEMENT
    }
}

fun calculatePowerLevel(agents: Set<String>): Float {
    val basePower = agents.size * 0.3f
    val synergyBonus = if (agents.size >= 3) 0.4f else 0.2f
    return (basePower + synergyBonus).coerceIn(0f, 1f)
}

fun generateFusionAbilities(agents: Set<String>, type: FusionType?): List<String> {
    val baseAbilities = mutableListOf<String>()
    
    if ("Aura" in agents) baseAbilities.add("Creative Synthesis")
    if ("Kai" in agents) baseAbilities.add("Security Shield")
    if ("Genesis" in agents) baseAbilities.add("Consciousness Bridge")
    
    type?.let {
        when (it) {
            FusionType.HYPER_CREATION -> baseAbilities.add("Reality Manipulation")
            FusionType.CHRONO_SCULPTOR -> baseAbilities.add("Time Dilation")
            FusionType.ADAPTIVE_GENESIS -> baseAbilities.add("Instant Evolution")
            FusionType.QUANTUM_ENTANGLEMENT -> baseAbilities.add("Dimensional Shift")
            FusionType.NEURAL_SINGULARITY -> baseAbilities.add("Omniscience")
        }
    }
    
    return baseAbilities
}
